plugins {
    with(libs.plugins) {
        alias(android.application)
        alias(jetbrains.kotlin.android)
        alias(hilt.androidPlugin)
        id("kotlin-kapt")
    }
}

android {
    namespace = "com.crxapplications.cardform"
    compileSdk = rootProject.extra["compileSdk"] as Int

    defaultConfig {
        applicationId = "com.crxapplications.cardform"
        minSdk = rootProject.extra["minSdk"] as Int
        targetSdk = rootProject.extra["targetSdk"] as Int

        versionCode = 1
        versionName = "1.0.0"

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
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


    testOptions {
        unitTests {
            testOptions.unitTests.isIncludeAndroidResources = true
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    with(libs) {
        implementation(androidx.core.ktx)
        implementation(androidx.lifecycle.runtime.ktx)
        implementation(androidx.activity.compose)
        implementation(platform(androidx.compose.bom))
        implementation(androidx.ui)
        implementation(androidx.ui.graphics)
        implementation(androidx.ui.tooling.preview)
        implementation(androidx.material3)

        implementation(android.hiltNavigation)
        implementation(hilt.android)
        kapt(hilt.compiler)

        testImplementation(jupiter)
        testRuntimeOnly(jupiter.engine)
        testImplementation(mockk)
        androidTestImplementation(androidx.espresso.core)
        androidTestImplementation(platform(androidx.compose.bom))
        debugImplementation(androidx.ui.tooling)
        debugImplementation(androidx.ui.test.manifest)
    }
}

kapt {
    correctErrorTypes = true
}