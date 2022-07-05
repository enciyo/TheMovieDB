plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("module-plugin")
}

android {
    compileSdk = Config.compileSdk
    defaultConfig {
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_KEY", "\"6433f805fdcce0b9dab579cf58caff9e\"")
        buildConfigField("String", "API_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "API_LANGUAGE", "\"en-US\"")
        buildConfigField("String", "API_IMAGE_PREF", "\"https://image.tmdb.org/t/p/w500/\"")
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
        targetCompatibility = Options.targetCompatibility
        sourceCompatibility = Options.sourceCompatibility
    }
    kotlinOptions {
        jvmTarget = Options.jvmTarget
    }
}

dependencies {
    implementation(Dependencies.coreKtx)
    implementation(Dependencies.moshi)
    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.retrofit)
    implementation(project(":domain"))
}