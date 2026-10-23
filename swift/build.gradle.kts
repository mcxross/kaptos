@file:OptIn(org.jetbrains.kotlin.gradle.swiftexport.ExperimentalSwiftExportDsl::class)

import org.jetbrains.kotlin.gradle.plugin.mpp.export.SwiftExportVisibility
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins { alias(libs.plugins.kotlin.multiplatform) }

kotlin {
  jvm {
    testRuns["test"].executionTask.configure { useJUnitPlatform() }
    val mainCompilation = compilations.getByName("main")
    compilations.create("swiftPackageTest") {
      associateWith(mainCompilation)
      defaultSourceSet.dependencies { implementation(libs.kotlin.test.junit5) }
    }
  }
  iosArm64()
  iosSimulatorArm64()
  iosX64()
  macosArm64()

  export {
    swift {
      moduleName = "AptosKit"
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

// The generated export task owns its destination. Assemble distributions directly from its output
// and the handwritten overlay so these tasks never overwrite each other's files.
val swiftOverlay = fileTree("overlay") { include("*.swift") }
val swiftPackageArchives =
  listOf("Debug", "Release").associateWith { configuration ->
    tasks.register<Tar>("archive${configuration}SwiftPackage") {
      group = "distribution"
      description = "Assembles the self-contained $configuration Swift package archive."
      dependsOn("export${configuration}SwiftPackage")
      archiveFileName.set("AptosKit-Swift-${project.version}-$configuration.tar.gz")
      destinationDirectory.set(rootProject.layout.buildDirectory.dir("distributions"))
      compression = Compression.GZIP
      isPreserveFileTimestamps = false
      isReproducibleFileOrder = true
      duplicatesStrategy = DuplicatesStrategy.FAIL
      into("AptosKit") {
        from(layout.buildDirectory.dir("swift-package/$configuration")) {
          exclude("**/.build/**", "**/.swiftpm/**", "LICENSE")
          // Ignore a stale overlay left by an older packaging command; use the source below.
          exclude(swiftOverlay.files.map { "Sources/AptosKit/${it.name}" })
        }
        from(swiftOverlay) { into("Sources/AptosKit") }
        from(rootProject.layout.projectDirectory.file("LICENSE"))
      }
    }
  }

val swiftPackageTestCompilation =
  kotlin.targets.getByName<KotlinJvmTarget>("jvm").compilations.getByName("swiftPackageTest")
val debugSwiftPackageArchive = swiftPackageArchives.getValue("Debug")
val swiftConsumerProject = rootProject.layout.projectDirectory.dir("sample/swiftApp")

tasks.register<Test>("checkSwiftPackage") {
  group = "verification"
  description = "Installs the Debug Swift package archive and runs Swift consumer tests and sample."
  dependsOn(debugSwiftPackageArchive)
  testClassesDirs = swiftPackageTestCompilation.output.classesDirs
  classpath =
    swiftPackageTestCompilation.output.allOutputs +
      swiftPackageTestCompilation.runtimeDependencyFiles
  useJUnitPlatform()
  inputs.file(debugSwiftPackageArchive.flatMap { it.archiveFile })
  inputs.files(
    fileTree(swiftConsumerProject) {
      include("Package.swift", "Sources/**", "Tests/**")
    }
  )
  systemProperty(
    "kaptos.swift.archive",
    debugSwiftPackageArchive.get().archiveFile.get().asFile.absolutePath,
  )
  systemProperty("kaptos.swift.consumer", swiftConsumerProject.asFile.absolutePath)
  systemProperty(
    "kaptos.swift.logs",
    layout.buildDirectory.dir("reports/swift-package").get().asFile.absolutePath,
  )
  // Swift/Xcode are external tools whose state is not represented by JVM test inputs.
  outputs.upToDateWhen { false }
}
