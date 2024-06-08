group = "xyz.mcxross.kaptos"

version = "1.5.0-SNAPSHOT"

plugins {
  alias(libs.plugins.androidApplication) apply false
  alias(libs.plugins.androidLibrary) apply false
  alias(libs.plugins.jetbrainsCompose) apply false
  alias(libs.plugins.kotlinMultiplatform) apply false
  alias(libs.plugins.graphqlMultiplatform) apply false
  alias(libs.plugins.kotlinSerialization) apply false
  alias(libs.plugins.jvm) apply false
  alias(libs.plugins.dokka) apply false
}

allprojects {
  repositories {
    mavenCentral()
    mavenLocal()
    google()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
  }
}
