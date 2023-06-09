plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlinx-serialization")
}

android {
    namespace = "ru.yo.excenssive_k"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.yo.excenssive_k"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName  = "1.0($versionCode)"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            versionNameSuffix = "-DEBUG"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions { jvmTarget = "17" }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions { kotlinCompilerExtensionVersion = "1.4.5" }
}

dependencies {

    val composeUiVersion = "1.4.3"
    val ktorVersion = "2.3.0"

    implementation ("androidx.core:core-ktx:1.10.1")

    implementation ("androidx.compose.ui:ui:$composeUiVersion")
    implementation ("androidx.compose.material3:material3:1.2.0-alpha01")
    implementation ("androidx.compose.ui:ui-tooling-preview:$composeUiVersion")

    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")

    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-logging:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
    implementation("io.ktor:ktor-serialization-gson:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
    implementation("io.ktor:ktor-utils:$ktorVersion")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
}

