pluginManagement {
  repositories {
    google()
    mavenCentral()
    mavenLocal()
    gradlePluginPortal()
    maven(url = "../repo")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
  }
  plugins {
    kotlin("jvm").version(extra["kotlin.version"] as String)
    kotlin("multiplatform").version(extra["kotlin.version"] as String)
    kotlin("plugin.serialization").version(extra["kotlin.version"] as String)
    kotlin("android").version(extra["kotlin.version"] as String)
    id("com.android.base").version(extra["agp.version"] as String)
    id("com.android.application").version(extra["agp.version"] as String)
    id("com.android.library").version(extra["agp.version"] as String)
    id("org.jetbrains.dokka").version(extra["kotlin.version"] as String)
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
