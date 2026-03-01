plugins { alias(libs.plugins.jvm) }

group = "xyz.mcxross.kaptos.sample"

dependencies {
  implementation(libs.kotlinx.coroutines.core)
  implementation(project(":kaptos"))
  testImplementation(kotlin("test"))
}

tasks.test { useJUnitPlatform() }

kotlin { jvmToolchain(17) }

kotlin { jvmToolchain(17) }
