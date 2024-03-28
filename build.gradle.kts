group = "xyz.mcxross.kaptos"
version = "1.0.0-SNAPSHOT"

plugins {
    kotlin("multiplatform") apply false
    id("com.android.library") apply false
}

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
        google()
    }
}
