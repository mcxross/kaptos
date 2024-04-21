import xyz.mcxross.graphql.plugin.gradle.config.GraphQLSerializer
import xyz.mcxross.graphql.plugin.gradle.graphql

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization")
    id("xyz.mcxross.graphql")
}

group = "xyz.mcxross.kaptos"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
    mavenLocal()
    google()
}

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

    macosArm64()
    macosX64()

    tvosX64()
    tvosArm64()

    watchosX64()
    watchosArm32()
    watchosArm64()

    mingwX64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        appleMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        commonMain.dependencies {
            implementation(libs.graphql.multiplatform.client)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.auth)
            implementation(libs.kotlinx.serialization.core)
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.mock)
        }
        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
        }
        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
        linuxMain.dependencies {
            implementation(libs.ktor.client.curl)
        }
        mingwMain.dependencies {
            implementation(libs.ktor.client.winhttp)
        }
    }
}

graphql {
    client {
        endpoint = "https://api.devnet.aptoslabs.com/v1/graphql"
        packageName = "xyz.mcxross.kaptos.generated"
        serializer = GraphQLSerializer.KOTLINX
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
