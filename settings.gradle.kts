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
  repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
}

plugins { id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0" }

rootProject.name = "kaptos"

include(":lib", ":sample:jvmApp")

project(":lib").name = "kaptos"

findProject(":sample:jvmApp")?.name = "jvmApp"
