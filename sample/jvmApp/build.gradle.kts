plugins {
  alias(libs.plugins.jvm)
  application
}

group = "xyz.mcxross.kaptos.sample"

dependencies {
  implementation(libs.kotlinx.coroutines.core)
  implementation(project(":kaptos"))
  implementation(project(":kaptos-encrypted-transactions"))
  implementation(project(":kaptos-confidential-assets"))
  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(17) }

application { mainClass.set("xyz.mcxross.kaptos.sample.MainKt") }
