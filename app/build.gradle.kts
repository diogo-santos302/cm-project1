plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example"
        minSdk = 26
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.navigation:navigation-compose:2.7.4")
    implementation("io.coil-kt:coil-compose:1.3.2")

    //Bottom Navigation
    implementation("androidx.compose.material:material:1.5.4")


    //Room
    implementation("androidx.room:room-runtime:2.6.0")
    implementation("com.google.android.gms:play-services-wearable:18.1.0")
    //implementation(project(mapOf("path" to ":wearosaccelerometerdata")))
    annotationProcessor("androidx.room:room-compiler:2.6.0")

    // To use Kotlin annotation processing tool (kapt)
    //kapt("androidx.room:room-compiler:2.6.0")
    // To use Kotlin Symbol Processing (KSP)
    //ksp("androidx.room:room-compiler:2.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Google Play services
    implementation("com.google.android.gms:play-services-location:21.0.1")

    implementation("com.google.code.gson:gson:2.9.0")

    // Firebase
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))
    implementation("com.google.firebase:firebase-messaging:23.3.1")
    implementation("com.google.firebase:firebase-database")
    implementation("com.google.firebase:firebase-functions:20.4.0")
//    implementation("com.google.firebase:firebase-messaging-directboot:23.3.1") // Needed to receive notifications while phone is locked

    // Charts
    implementation("co.yml:ycharts:2.1.0")

    // Testing
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    androidTestImplementation("androidx.navigation:navigation-testing:2.7.4")
    androidTestImplementation("androidx.test.espresso:espresso-intents:3.5.1")
}