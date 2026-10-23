@file:OptIn(org.jetbrains.kotlin.gradle.swiftexport.ExperimentalSwiftExportDsl::class)

import org.jetbrains.kotlin.gradle.plugin.mpp.export.SwiftExportVisibility

plugins { alias(libs.plugins.kotlin.multiplatform) }

kotlin {
  jvm { testRuns["test"].executionTask.configure { useJUnitPlatform() } }
  iosArm64()
  iosSimulatorArm64()
  iosX64()
  macosArm64()

  export {
    swift {
      moduleName = "Kaptos"
      rootPackage = "xyz.mcxross.kaptos.swift"
      xcodeIntegration {
        configure(libs.ktor.client.core, SwiftExportVisibility.HIDDEN)
        configure(libs.kotlinx.serialization.core, SwiftExportVisibility.HIDDEN)
      }
      swiftPackageIntegration {
        outputDirectory.set(layout.buildDirectory.dir("swift-package"))
        configure(libs.ktor.client.core, SwiftExportVisibility.HIDDEN)
        configure(libs.kotlinx.serialization.core, SwiftExportVisibility.HIDDEN)
      }
    }
  }

  sourceSets {
    commonMain.dependencies {
      implementation(project(":kaptos"))
      implementation(project(":kaptos-encrypted-transactions"))
      implementation(project(":kaptos-confidential-assets"))
      implementation(libs.kotlinx.coroutines.core)
      implementation(libs.kotlinx.serialization.core)
      implementation(libs.ktor.serialization.kotlinx.json)
    }
    commonTest.dependencies {
      implementation(kotlin("test"))
      implementation(libs.kotlinx.coroutines.test)
    }
    jvmTest.dependencies { implementation(libs.kotlin.test.junit5) }
  }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))
