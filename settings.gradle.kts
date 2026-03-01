pluginManagement {
  repositories {
    google()
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven(url = "../repo")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

dependencyResolutionManagement {
  repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
  repositories {
    google()
    mavenCentral()
    mavenLocal()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0" }

rootProject.name = "kaptos"

include(":lib", ":sample:jvmApp")

project(":lib").name = "kaptos"

findProject(":sample:jvmApp")?.name = "jvmApp"
