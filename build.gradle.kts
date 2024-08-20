group = "xyz.mcxross.kaptos"

version = "0.1.0-beta1"

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.graphql.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
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
