plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.musicplayer"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.musicplayer"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
// Module Dependencies:
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.composeBom))
    implementation(libs.bundles.ui.compose)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.composeBom))
    androidTestImplementation(libs.uiTestJUnit4)
    debugImplementation(libs.uiTooling)
    debugImplementation(libs.uiTestManifest)
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
    implementation(libs.navigation.compose)
    implementation(libs.coilCompose)
    implementation(libs.bundles.paging)
    implementation(libs.coilCompose)
    implementation(libs.androidx.media3.session)
    implementation (libs.gson)
    debugImplementation(libs.leakcanary.android)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)
    implementation ("androidx.tracing:tracing:1.1.0")

}

kapt {
    correctErrorTypes = true
}