plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.aurynoffline" // <-- adicione esta linha
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.aurynoffline"
        minSdk = 21
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
}
