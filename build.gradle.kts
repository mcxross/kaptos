group = "xyz.mcxross.kaptos"

version = "1.0.1-SNAPSHOT"

plugins {
  id("xyz.mcxross.graphql") version "1.0.0-SNAPSHOT" apply false
  kotlin("plugin.serialization") apply false

  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.jvm) apply false
}

allprojects {
  repositories {
    mavenCentral()
    mavenLocal()
    google()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
  }
}
