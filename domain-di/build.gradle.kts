plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.hen.di.domain"
    compileSdk = 36

    defaultConfig {
        minSdk = 27
        consumerProguardFiles("consumer-rules.pro")
    }
    kotlin { jvmToolchain(17) }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}