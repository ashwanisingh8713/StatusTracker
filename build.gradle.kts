plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kobweb.library) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.kobweb.application) apply false
    alias(libs.plugins.compose.compiler) apply false
}


allprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}