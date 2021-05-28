import java.text.SimpleDateFormat
import java.util.*

/**
 * @desc：所有依赖库按照规范写在这里，用到的地方，通过 Deps 引用即可。在添加依赖之前，请检查该文件中是否已存在相
 *        同的依赖，如若已经添加相同依赖，则无需再重复添加，请注意。
 * @date：2021/1/16 19:08
 */
object Deps {

    const val applicationId = "com.fphoenixcorneae.jetpackmvvm.demo"

    object FPhoenixCorneaE {
        const val commonUtil = "com.github.FPhoenixCorneaE:CommonUtil:1.1.1"
        const val commonTitlebar = "com.github.FPhoenixCorneaE:CommonTitlebar:1.0.5"
        const val jetpackMvvm = "com.github.FPhoenixCorneaE:JetpackMvvm:1.0.0"
    }

    object GradlePlugin {
        const val gradle = "com.android.tools.build:gradle:4.2.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
    }

    object PluginIds {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlinAndroid = "android"
        const val kotlinParcelize = "kotlin-parcelize"
        const val kotlinKapt = "kapt"
        const val mavenPublish = "org.gradle.maven-publish"
    }

    object Versions {
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.3"
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val versionName = "1.0.0"
        const val versionCode = 100
        const val kotlinVersion = "1.5.0"
    }

    object BuildType {
        const val Debug = "debug"
        const val Release = "release"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinVersion}"
    }

    /**
     * 协程
     */
    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val material = "com.google.android.material:material:1.2.1"
        const val coreKtx = "androidx.core:core-ktx:1.3.2"
        const val activityKtx = "androidx.activity:activity-ktx:1.2.2"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.3"
    }

    object Lifecycle {
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.3.1"
        const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.3.1"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    }

    object Navigation {
        const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:2.3.5"
        const val commonKtx = "androidx.navigation:navigation-common-ktx:2.3.5"
        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.3.5"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:2.3.5"
    }

    object Startup {
        const val runtime = "androidx.startup:startup-runtime:1.0.0"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }

    /** Kotlin Coroutines Image Loader */
    object Coil {
        private const val version = "1.2.1"
        const val coil = "io.coil-kt:coil:$version"
        const val gif = "io.coil-kt:coil-gif:$version"
        const val svg = "io.coil-kt:coil-svg:$version"
        const val video = "io.coil-kt:coil-video:$version"
    }

    /** CoilTransformations */
    object CoilTransformations {
        private const val version = "1.0.0"
        const val transformations =
            "com.github.Commit451.coil-transformations:transformations:$version"
        const val transformationsGpu =
            "com.github.Commit451.coil-transformations:transformations-gpu:$version"
        const val transformationsFaceDetection =
            "com.github.Commit451.coil-transformations:transformations-face-detection:$version"
    }

    object DataStore {
        const val mmkv = "com.tencent:mmkv-static:1.2.8"
    }

    /** Powerful and flexible RecyclerAdapter */
    const val baseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"

    /**
     * 当前时间
     */
    fun getSystemTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        return simpleDateFormat.format(System.currentTimeMillis())
    }
}
