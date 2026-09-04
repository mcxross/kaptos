import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinMultiplatform
import com.vanniktech.maven.publish.SourcesJar
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.android.kotlin.multiplatform.library)
  alias(libs.plugins.dokka)
  alias(libs.plugins.kotlin.multiplatform)
  alias(libs.plugins.kotlin.serialization)
  alias(libs.plugins.maven.publish)
}

group = "xyz.mcxross.kaptos"

kotlin {
  jvm { testRuns["test"].executionTask.configure { useJUnitPlatform() } }
  android {
    namespace = "xyz.mcxross.kaptos.keyless"
    compileSdk {
      version = release(37) { minorApiLevel = 0 }
    }
    minSdk = 24
    compilerOptions.jvmTarget = JvmTarget.JVM_17
  }
  iosX64()
  iosArm64()
  iosSimulatorArm64()
  macosArm64()
  macosX64()
  applyDefaultHierarchyTemplate()

  sourceSets {
    commonMain.dependencies {
      api(project(":kaptos"))
      implementation(libs.fastkrypto)
      implementation(libs.ktor.client.content.negotiation)
      api(libs.ktor.client.core)
      implementation(libs.ktor.serialization.kotlinx.json)
      api(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.datetime)
      implementation(libs.kotlinx.serialization.core)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(libs.ktor.client.mock)
      implementation(libs.kotlinx.coroutines.test)
    }
    jvmTest.dependencies { implementation(libs.kotlin.test.junit5) }
  }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks.withType<DokkaTask>().configureEach {
  notCompatibleWithConfigurationCache("https://github.com/Kotlin/dokka/issues/2231")
}

dokka {
  moduleName.set("Kaptos Keyless")
  dokkaPublications.html { suppressInheritedMembers.set(true) }
}

mavenPublishing {
  val enableSigning =
    providers.gradleProperty("enableSigning").orNull?.toBooleanStrictOrNull() ?: true

  coordinates("xyz.mcxross.kaptos", "kaptos-keyless", version.toString())
  configure(
    KotlinMultiplatform(
      javadocJar = JavadocJar.Dokka("dokkaGenerate"),
      sourcesJar = SourcesJar.Sources(),
    )
  )
  pom {
    name.set("Kaptos Keyless")
    description.set("Standard and federated Aptos Keyless accounts for Kaptos")
    inceptionYear.set("2026")
    url.set("https://github.com/mcxross/kaptos")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
        distribution.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
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
      url.set("https://github.com/mcxross/kaptos")
      connection.set("scm:git:ssh://github.com/mcxross/kaptos.git")
      developerConnection.set("scm:git:ssh://github.com/mcxross/kaptos.git")
    }
  }
  publishToMavenCentral(automaticRelease = true)
  if (enableSigning) signAllPublications()
}
