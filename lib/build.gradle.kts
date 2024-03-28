plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

group = "xyz.mcxross.kaptos"
version = "1.0.0-SNAPSHOT"

kotlin {
    jvm {
        jvmToolchain(17)
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }

    androidTarget { publishLibraryVariants("release", "debug") }

    js {
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    linuxX64()
    linuxArm64()

    macosArm64()
    macosX64()

    tvosX64()
    tvosArm64()

    watchosX64()
    watchosArm32()
    watchosArm64()

    mingwX64()

    sourceSets {
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

android {
    namespace = "mcxross.kaptos"
    defaultConfig {
        minSdk = 24
        compileSdk = 33
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
}