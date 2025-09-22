plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.hen.data"
    compileSdk = 36

    defaultConfig {
        minSdk = 27
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
            )
        }
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.material)

    implementation(libs.retrofit)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serialization.converter)

    implementation(libs.hilt.core)
    implementation(libs.room.ktx)
    implementation(libs.room)
    ksp(libs.hilt.compiler)
    ksp(libs.room.compiler)

    testImplementation(libs.room.testing)
    testImplementation(libs.robolectric)
    testImplementation(libs.core)
    testImplementation(libs.turbine)

    testImplementation(libs.mockk.android)
    testImplementation(libs.mockk.core)
    testImplementation(libs.coroutines.test)

    testImplementation(libs.junit)
    testImplementation(libs.kotlin.test.junit)

    testImplementation(libs.mockwebserver)
}