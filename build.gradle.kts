import kotlinx.validation.ExperimentalBCVApi
import kotlinx.validation.KotlinApiBuildTask
import kotlinx.validation.KotlinApiCompareTask
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

group = "xyz.mcxross.kaptos"

plugins {
  alias(libs.plugins.android.application) apply false
  alias(libs.plugins.android.kotlin.multiplatform.library) apply false
  alias(libs.plugins.apollo.graphql) apply false
  alias(libs.plugins.binary.compatibility.validator)
  alias(libs.plugins.dokka) apply false
  alias(libs.plugins.jetbrains.compose) apply false
  alias(libs.plugins.jvm) apply false
  alias(libs.plugins.kotest) apply false
  alias(libs.plugins.kotlin.multiplatform) apply false
  alias(libs.plugins.kotlin.serialization) apply false
}

subprojects { version = rootProject.version }

apiValidation {
  ignoredProjects.add("jvmApp")
  @OptIn(ExperimentalBCVApi::class) klib { enabled = true }
}

// BCV 0.18 does not discover the new Android KMP target automatically. Wire it into the same
// dump/check lifecycle so Android snapshots are generated from compiled classes, not copied.
subprojects {
  plugins.withId("com.android.kotlin.multiplatform.library") {
    afterEvaluate {
      val kotlin = extensions.getByType<KotlinMultiplatformExtension>()
      val androidMain = kotlin.targets.getByName("android").compilations.getByName("main")
      val jvmBuild = tasks.named<KotlinApiBuildTask>("jvmApiBuild")
      val androidBuild =
        tasks.register<KotlinApiBuildTask>("androidApiBuild") {
          group = "verification"
          inputClassesDirs.from(androidMain.output.classesDirs)
          runtimeClasspath.from(jvmBuild.map { it.runtimeClasspath })
          outputApiFile.set(layout.buildDirectory.file("api/android/${project.name}.api"))
        }
      val snapshot = layout.projectDirectory.file("api/android/${project.name}.api")
      val androidDump =
        tasks.register<Copy>("androidApiDump") {
          from(androidBuild.flatMap { it.outputApiFile })
          into(snapshot.asFile.parentFile)
          rename { snapshot.asFile.name }
        }
      val androidCheck =
        tasks.register<KotlinApiCompareTask>("androidApiCheck") {
          projectApiFile.set(snapshot)
          generatedApiFile.set(androidBuild.flatMap { it.outputApiFile })
        }
      tasks.named("apiDump") { dependsOn(androidDump) }
      tasks.named("apiCheck") { dependsOn(androidCheck) }
    }
  }
}
