import java.text.SimpleDateFormat
import java.util.*

/**
 * @desc：所有依赖库按照规范写在这里，用到的地方，通过 Deps 引用即可。在添加依赖之前，请检查该文件中是否已存在相
 *       同的依赖，如若已经添加相同依赖，则无需再重复添加，请注意。
 * @date：2021/1/16 19:08
 */
object Deps {

    object Version {
        const val agpVersion = "7.0.4"
        const val kotlinVersion = "1.6.10"
    }

    object FPhoenixCorneaE {
        const val common = "com.github.FPhoenixCorneaE:Common:2.0.4"
        const val commonToolbar = "com.github.FPhoenixCorneaE:CommonToolbar:3.0.0"
        const val jetpackMvvm = "com.github.FPhoenixCorneaE:JetpackMvvm:${DefaultConfig.versionName}"
    }

    object PluginId {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlin = "org.jetbrains.kotlin.android"
        const val kotlinKapt = "kapt"
    }

    object DefaultConfig {
        const val applicationId = "com.fphoenixcorneae.jetpackmvvm.demo"
        const val compileSdk = 32
        const val buildToolsVersion = "32.0.0"
        const val minSdk = 21
        const val targetSdk = 32
        const val versionCode = 130
        const val versionName = "1.3.0"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    object BuildType {
        const val Debug = "debug"
        const val Release = "release"
    }

    object Kotlin {
        const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:${Version.kotlinVersion}"
    }

    object AndroidX {
        const val multiDex = "androidx.multidex:multidex:2.0.1"
        const val appcompat = "androidx.appcompat:appcompat:1.4.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.3"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.1"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val material = "com.google.android.material:material:1.4.0"
        const val coreKtx = "androidx.core:core-ktx:1.7.0"

        // activity
        const val activityKtx = "androidx.activity:activity-ktx:1.4.0"

        // fragment
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.4.1"
    }

    object Lifecycle {
        private const val version = "2.4.1"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
        const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
        const val process = "androidx.lifecycle:lifecycle-process:$version"
        const val service = "androidx.lifecycle:lifecycle-service:$version"
        const val compiler = "androidx.lifecycle:lifecycle-compiler:$version"
    }

    object Navigation {
        private const val version = "2.4.2"
        const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:$version"
        const val commonKtx = "androidx.navigation:navigation-common-ktx:$version"
        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
    }

    object Startup {
        const val runtime = "androidx.startup:startup-runtime:1.1.1"
    }

    object Test {
        const val junit = "junit:junit:4.13.2"
        const val junitExt = "androidx.test.ext:junit:1.1.3"
        const val junitExtKtx = "androidx.test.ext:junit-ktx:1.1.3"
        const val espresso = "androidx.test.espresso:espresso-core:3.4.0"
    }

    /** Kotlin Coroutines Image Loader ：https://coil-kt.github.io/coil/ */
    object Coil {
        private const val version = "1.3.2"
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
        const val mmkv = "com.tencent:mmkv-static:1.2.13"
    }

    /** Powerful and flexible RecyclerAdapter */
    const val baseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.7"

    /**
     * 当前时间
     */
    fun getSystemTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        return simpleDateFormat.format(System.currentTimeMillis())
    }
}
