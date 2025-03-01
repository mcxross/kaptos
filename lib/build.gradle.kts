import java.net.URL
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import xyz.mcxross.graphql.plugin.gradle.graphql
import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SonatypeHost

plugins {
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.graphql.multiplatform)
  alias(libs.plugins.dokka)
  alias(libs.plugins.maven.publish)
}

group = "xyz.mcxross.kaptos"

version = "0.1.2-beta"

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

  val xcframeworkName = "AptosKit"
  val xcf = XCFramework(xcframeworkName)

  listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach {
    it.binaries.framework {
      baseName = xcframeworkName
      binaryOption("bundleId", "xyz.mcxross.${xcframeworkName}")
      xcf.add(this)
      isStatic = true
    }
  }

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
      implementation(libs.bcs)
      implementation(libs.kotlinx.datetime)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.serialization.core)
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
  }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

android {
  namespace = "xy.mcxross.kaptos"
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

mavenPublishing {
  coordinates("xyz.mcxross.kaptos", "kaptos", version.toString())

  configure(
    KotlinMultiplatform(
      javadocJar = JavadocJar.Dokka("dokkaHtml"),
      sourcesJar = true,
      androidVariantsToPublish = listOf("debug", "release"),
    )
  )

  pom {
    name.set("Kaptos")
    description.set("Multiplatform SDK for integrating with the Aptos blockchain")
    inceptionYear.set("2023")
    url.set("https://github.com/mcxross")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("mcxross")
        name.set("Mcxross")
        email.set("oss@mcxross.xyz")
        url.set("https://mcxross.xyz/")
      }
    }
    scm {
      url.set("https://github.com/mcxross/kaptos")
      connection.set("scm:git:ssh://github.com/mcxross/kaptos.git")
      developerConnection.set("scm:git:ssh://github.com/mcxross/kaptos.git")
    }
  }

  publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)

  signAllPublications()
}
