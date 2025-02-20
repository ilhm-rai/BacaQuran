plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.codetarian.bacaquran"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.codetarian.bacaquran"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    packaging {
        resources {
            excludes.add("META-INF/atomicfu.kotlin_module")
        }
    }
    androidResources {
        noCompress += "tflite"
    }
}

dependencies {
    val activityVersion = "1.8.2"
    val appCompatVersion = "1.6.1"
    val constraintLayoutVersion = "2.1.4"
    val coreTestingVersion = "2.2.0"
    val coroutines = "1.8.0"
    val lifecycleVersion = "2.7.0"
    val materialVersion = "1.11.0"
    val roomVersion = "2.6.1"
    val hiltVersion = "2.45"
    // testing
    val junitVersion = "4.13.2"
    val espressoVersion = "3.5.1"
    val androidxJunitVersion = "1.1.5"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.appcompat:appcompat:${appCompatVersion}")
    implementation("com.google.android.material:material:${materialVersion}")
    implementation("androidx.constraintlayout:constraintlayout:${constraintLayoutVersion}")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
    implementation("io.github.java-diff-utils:java-diff-utils:4.12")
    implementation("androidx.test.espresso:espresso-idling-resource:${espressoVersion}")

    // Lifecycle Components
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${lifecycleVersion}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${lifecycleVersion}")

    // ViewModel Components
    implementation("androidx.fragment:fragment-ktx:1.6.2")
    implementation("androidx.activity:activity-ktx:${activityVersion}")

    // Room Components
    implementation("androidx.room:room-ktx:${roomVersion}")
    ksp("androidx.room:room-compiler:${roomVersion}")
    androidTestImplementation("androidx.room:room-testing:${roomVersion}")

    // Kotlin Components
    // implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.9.22")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-core:${coroutines}")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutines}")
    implementation("javax.inject:javax.inject:1")

    // DeepSpeech
    implementation("org.deepspeech:libdeepspeech:0.9.3")

    // Hilt
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0-alpha03")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.38.1")
    implementation("com.google.dagger:hilt-android:2.38.1")
    ksp("com.google.dagger:hilt-compiler:2.37")
    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    ksp("androidx.hilt:hilt-compiler:1.0.0")

    testImplementation("junit:junit:${junitVersion}")
    androidTestImplementation("androidx.arch.core:core-testing:${coreTestingVersion}")
    androidTestImplementation("androidx.test.ext:junit:${androidxJunitVersion}")

    // Espresso
    androidTestImplementation("androidx.test.espresso:espresso-core:${espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-contrib:${espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-intents:${espressoVersion}")
    androidTestImplementation("androidx.test.espresso:espresso-idling-resource:${espressoVersion}")
}