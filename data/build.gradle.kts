
plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    implementation (project(":domain"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.runner)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    implementation(libs.retrofit)
    implementation(libs.converterGson)
    implementation(libs.bundles.paging)
    implementation("androidx.legacy:legacy-support-v4:1.0.0") // Needed MediaSessionCompat.Token
// Media3
    implementation("androidx.media3:media3-exoplayer:1.0.1")
    implementation("androidx.media3:media3-ui:1.0.1")
    implementation("androidx.media3:media3-session:1.0.1")

    implementation("com.github.bumptech.glide:glide:4.15.1")

}

kapt {
    correctErrorTypes = true
}

