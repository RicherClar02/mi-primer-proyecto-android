plugins {
    id("com.android.application")
}

android {
    namespace = "com.richer.primerproyecto"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.richer.primerproyecto"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
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

    androidResources {
        // Idiomas que realmente se empaquetan en el APK.
        localeFilters += listOf("es", "en", "fr", "de")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lint {
        // Que un error de lint detenga la compilacion, para no dejar fallos sueltos.
        abortOnError = true
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("androidx.core:core:1.16.0")
    implementation("androidx.activity:activity:1.10.1")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("com.google.android.material:material:1.12.0")
}
