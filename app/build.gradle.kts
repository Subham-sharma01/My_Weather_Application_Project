plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.meraprojects.weatherapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.meraprojects.weatherapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation("com.android.volley:volley-cronet:1.2.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.squareup.picasso:picasso:2.8")
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}