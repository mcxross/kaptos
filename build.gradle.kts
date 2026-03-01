group = "xyz.mcxross.kaptos"

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.apollo.graphql) apply false
  alias(libs.plugins.dokka) apply false
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.jvm) apply false
  alias(libs.plugins.kotest) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
}

subprojects { version = rootProject.version }
