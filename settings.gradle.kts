pluginManagement {
  repositories {
    google()
    mavenLocal()
    mavenCentral()
    gradlePluginPortal()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0" }

// Keep the build identity distinct from the published `:lib` project name so
// type-safe project accessors remain unambiguous when enabled.
rootProject.name = "kaptos-build"

include(":lib", ":encrypted-transactions", ":confidential-assets", ":sample:jvmApp")

project(":lib").name = "kaptos"
project(":encrypted-transactions").name = "kaptos-encrypted-transactions"
project(":confidential-assets").name = "kaptos-confidential-assets"

findProject(":sample:jvmApp")?.name = "jvmApp"
