import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("com.android.application")
    // kotlin
    id("kotlin-android")
    id("kotlin-kapt")
    // hilt
    id("dagger.hilt.android.plugin")
}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    compileSdk = 31
    buildToolsVersion = "31.0.0"

    defaultConfig {
        applicationId = "com.superyao.quick1922"
        minSdk = 24
        targetSdk = 31
        versionCode = 6
        versionName = "1.1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        setProperty("archivesBaseName", "Quick1922-$versionName-$versionCode")
    }

    signingConfigs {
        register("release") {
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
        }
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
//            isMinifyEnabled = true
//            isShrinkResources = true
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        disable("ContentDescription", "SpUsage")
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar", "*.aar")))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.30")

    val coroutinesVersion = "1.5.2"
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.fragment:fragment-ktx:1.3.6")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")

    implementation("androidx.constraintlayout:constraintlayout:2.1.0")

    // lifecycle

    val lifecycleVersion = "2.3.1"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    // Hilt

    val hiltVersion = "2.38.1"
    implementation("com.google.dagger:hilt-android:$hiltVersion")
    kapt("com.google.dagger:hilt-compiler:$hiltVersion")

    // core

    implementation("com.journeyapps:zxing-android-embedded:4.2.0")

    // test

    testImplementation("junit:junit:4.13.2")
    testImplementation("io.mockk:mockk:1.12.0")

    androidTestImplementation("androidx.test:runner:1.4.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    androidTestImplementation("androidx.test.ext:junit:1.1.3")

    // Others

    implementation("com.github.SUPERYAO541:android-toolkit:0.2.1")

    /*
    Third party
     */

    // debug

    // https://square.github.io/leakcanary/changelog/
    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.7")

    implementation("com.jakewharton.timber:timber:5.0.1")
}