plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.android.extensions")
}

android {
    compileSdkVersion(Deps.Versions.compileSdkVersion)
    buildToolsVersion(Deps.Versions.buildToolsVersion)

    defaultConfig {
        applicationId = "com.fphoenixcorneae.core.demo"
        minSdkVersion(Deps.Versions.minSdkVersion)
        targetSdkVersion(Deps.Versions.targetSdkVersion)
        versionCode = Deps.Versions.versionCode
        versionName = Deps.Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    dexOptions {
        jumboMode = true
    }

    lintOptions {
        isAbortOnError = false
    }

    androidExtensions {
        isExperimental = true
    }

    packagingOptions {
        exclude("META-INF/proguard/coroutines.pro")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdLibJdk7)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.recyclerView)
    implementation(Deps.AndroidX.lifecycleRuntimeKtx)
    implementation(project(mapOf("path" to ":core")))
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.junitExt)
    androidTestImplementation(Deps.Test.espresso)

}