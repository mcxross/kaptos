import java.net.URL
import java.util.*
import org.jetbrains.dokka.gradle.DokkaTask
import xyz.mcxross.graphql.plugin.gradle.config.GraphQLSerializer
import xyz.mcxross.graphql.plugin.gradle.graphql

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  kotlin("plugin.serialization")
  id("xyz.mcxross.graphql")
  id("maven-publish")
  id("org.jetbrains.dokka")
}

group = "xyz.mcxross.kaptos"

version = "1.0.4-SNAPSHOT"

repositories {
  mavenCentral()
  mavenLocal()
  google()
}

kotlin {
  jvm {
    jvmToolchain(17)
    testRuns.named("test") { executionTask.configure { useJUnitPlatform() } }
  }

  androidTarget { publishLibraryVariants("release", "debug") }

  js { browser { commonWebpackConfig { cssSupport { enabled.set(true) } } } }

  iosX64()
  iosArm64()
  iosSimulatorArm64()

  linuxX64()

  macosArm64()
  macosX64()

  tvosX64()
  tvosArm64()

  watchosX64()
  watchosArm32()
  watchosArm64()

  mingwX64()

  applyDefaultHierarchyTemplate()

  sourceSets {
    val androidJvmMain by creating {
      dependsOn(commonMain.get())
      dependencies { implementation(libs.bcprov.jdk15on) }
    }
    val androidMain by getting {
      dependsOn(androidJvmMain)
      dependencies { implementation(libs.ktor.client.okhttp) }
    }
    appleMain.dependencies { implementation(libs.ktor.client.darwin) }
    commonMain.dependencies {
      implementation(libs.graphql.multiplatform.client)
      implementation(libs.ktor.client.core)
      implementation(libs.ktor.client.logging)
      implementation(libs.ktor.client.content.negotiation)
      implementation(libs.ktor.serialization.kotlinx.json)
      implementation(libs.ktor.client.auth)
      implementation(libs.kotlinx.serialization.core)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.ktor.client.mock)
    }
    val jvmMain by getting {
      dependsOn(androidJvmMain)
      dependencies { implementation(libs.ktor.client.cio) }
    }
    jsMain.dependencies { implementation(libs.ktor.client.js) }
    linuxMain.dependencies { implementation(libs.ktor.client.curl) }
    mingwMain.dependencies { implementation(libs.ktor.client.winhttp) }
  }
}

graphql {
  client {
    endpoint = "https://api.devnet.aptoslabs.com/v1/graphql"
    packageName = "xyz.mcxross.kaptos.generated"
    serializer = GraphQLSerializer.KOTLINX
  }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

android {
  namespace = "mcxross.kaptos"
  defaultConfig {
    minSdk = 24
    compileSdk = 33
  }

  sourceSets {
    named("main") {
      manifest.srcFile("src/androidMain/AndroidManifest.xml")
      res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
  }
}

ext["sonatypeUser"] = null

ext["sonatypePass"] = null

val secretPropsFile = project.rootProject.file("local.properties")

if (secretPropsFile.exists()) {
  secretPropsFile
    .reader()
    .use { Properties().apply { load(it) } }
    .onEach { (name, value) -> ext[name.toString()] = value }
} else {
  ext["sonatypeUser"] = System.getenv("OSSRH_USERNAME")
  ext["sonatypePass"] = System.getenv("OSSRH_PASSWORD")
}

tasks.getByName<DokkaTask>("dokkaHtml") {
  moduleName.set("Kaptos")
  outputDirectory.set(file(layout.buildDirectory.dir("dokka").get().asFile))
  dokkaSourceSets {
    configureEach {
      includes.from("Module.md")
      sourceLink {
        localDirectory.set(file("commonMain/kotlin"))
        remoteUrl.set(
          URL("https://github.com/mcxross/kaptos/blob/master/lib/src/commonMain/kotlin")
        )
        remoteLineSuffix.set("#L")
      }
    }
  }
}

tasks.withType<DokkaTask>().configureEach {
  notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/2231")
}

val javadocJar =
  tasks.register<Jar>("javadocJar") {
    archiveClassifier.set("javadoc")
    dependsOn("dokkaHtml")
    from(layout.buildDirectory.dir("dokka").get().asFile)
  }

publishing {
  if (hasProperty("sonatypeUser") && hasProperty("sonatypePass")) {
    repositories {
      maven {
        name = "sonatype"
        val isSnapshot = version.toString().endsWith("-SNAPSHOT")
        setUrl(
          if (isSnapshot) {
            "https://s01.oss.sonatype.org/content/repositories/snapshots/"
          } else {
            "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
          }
        )
        credentials {
          username = "" //property("sonatypeUser") as String
          password = "" //property("sonatypePass") as String
        }
      }
    }
  }

  publications.withType<MavenPublication> {
    artifact(javadocJar.get())
    pom {
      name.set("Kaptos")
      description.set(
        "Kaptos is a Kotlin Multiplatform library for interacting with the Aptos blockchain."
      )
      url.set("https://github.com/mcxross")

      licenses {
        license {
          name.set("Apache License, Version 2.0")
          url.set("https://opensource.org/licenses/APACHE-2.0")
        }
      }
      developers {
        developer {
          id.set("mcxross")
          name.set("Mcxross")
          email.set("oss@mcxross.xyz")
        }
      }
      scm {
        connection.set("scm:git:git://github.com/mcxross/kaptos.git")
        developerConnection.set("scm:git:ssh://github.com/mcxross/kaptos.git")
        url.set("https://github.com/mcxross/kaptos")
      }
    }
  }
}
