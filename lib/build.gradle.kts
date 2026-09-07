import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SourcesJar
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.apollo.graphql)
  alias(libs.plugins.dokka)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.maven.publish)
}

group = "xyz.mcxross.kaptos"

kotlin {
  jvm { testRuns["test"].executionTask.configure { useJUnitPlatform() } }

  android {
    namespace = "xyz.mcxross.kaptos"
    compileSdk {
      version = release(37) { minorApiLevel = 0 }
    }
    minSdk = 24
    compilerOptions.jvmTarget = JvmTarget.JVM_17
    withDeviceTestBuilder { sourceSetTreeName = "test" }
      .configure {
        instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
      }
  }

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

  macosArm64()
  macosX64()

  applyDefaultHierarchyTemplate()

  sourceSets {
    val androidJvmMain by creating { dependsOn(commonMain.get()) }
    val androidMain by getting {
      dependsOn(androidJvmMain)
      dependencies {
        implementation(libs.ktor.client.okhttp)
        implementation(libs.fastkrypto.android)
      }
    }
    val androidDeviceTest by getting {
      dependencies {
        implementation(libs.androidx.test.ext.junit)
        implementation(libs.androidx.test.runner)
      }
    }
    appleMain.dependencies { implementation(libs.ktor.client.darwin) }
    commonMain.dependencies {
      implementation(libs.apollo.runtime)
      implementation(libs.bcs)
      implementation(libs.ktor.client.auth)
      implementation(libs.ktor.client.content.negotiation)
      api(libs.ktor.client.core)
      implementation(libs.ktor.client.logging)
      implementation(libs.ktor.serialization.kotlinx.json)
      implementation(libs.kotlinx.datetime)
      api(libs.kotlinx.coroutines.core)
      api(libs.kotlinx.serialization.core)
      implementation(libs.kotlin.result)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(libs.kotest.assertions.core)
      implementation(libs.kotest.framework.engine)
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.serialization.core)
      implementation(libs.ktor.client.mock)
    }
    val jvmMain by getting {
      dependsOn(androidJvmMain)
      dependencies {
        implementation(libs.ktor.client.cio)
        implementation(libs.fastkrypto.jvm)
      }
    }
    val jvmTest by getting {
      dependencies {
        implementation(libs.kotest.runner.junit5)
        implementation(libs.kotlin.test.junit5)
      }
    }
    iosArm64Main.dependencies { implementation(libs.fastkrypto.iosarm64) }
    iosX64Main.dependencies { implementation(libs.fastkrypto.iosx64) }
    iosSimulatorArm64Main.dependencies { implementation(libs.fastkrypto.iossimulatorarm64) }
    macosArm64Main.dependencies { implementation(libs.fastkrypto.macosarm64) }
    macosX64Main.dependencies { implementation(libs.fastkrypto.macosx64) }
  }
}

apollo {
  service("service") {
    packageName.set("xyz.mcxross.kaptos.generated")
    generateAsInternal.set(true)
  }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

// fastkrypto's android native artifacts are for device/emulator, not host JVM unit tests.
tasks.withType<Test>().configureEach {
  if (name.endsWith("UnitTest")) {
    enabled = false
  }
}

tasks.withType<DokkaTask>().configureEach {
  notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/2231")
}

dokka {
  moduleName.set("Kaptos")
  dokkaPublications.html { suppressInheritedMembers.set(true) }
  dokkaSourceSets {
    configureEach {
      includes.from("Module.md")
      sourceLink {
        localDirectory.set(file("commonMain/kotlin"))
        remoteUrl("https://github.com/mcxross/kaptos/blob/master/lib/src/commonMain/kotlin")
        remoteLineSuffix.set("#L")
      }
    }
  }
  dokkaPublications.html { outputDirectory.set(layout.buildDirectory.dir("dokka")) }
}

mavenPublishing {
  val enableSigning =
    providers.gradleProperty("enableSigning").orNull?.toBooleanStrictOrNull() ?: true

  coordinates("xyz.mcxross.kaptos", "kaptos", version.toString())

  configure(
    KotlinMultiplatform(
      javadocJar = JavadocJar.Dokka("dokkaGenerate"),
      sourcesJar = SourcesJar.Sources(),
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

  publishToMavenCentral(automaticRelease = true)

  if (enableSigning) {
    signAllPublications()
  }
}
