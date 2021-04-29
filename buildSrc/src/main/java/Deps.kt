import java.text.SimpleDateFormat
import java.util.*

/**
 * @desc：所有依赖库按照规范写在这里，用到的地方，通过Deps引用即可。在添加依赖之前，请检查该文件中是否已存在相
 *        同的依赖，如若已经添加相同依赖，则无需再重复添加，请注意。
 * @date：2021/1/16 19:08
 */
object Deps {

    const val applicationId = "com.fphoenixcorneae.core.demo"

    object FPhoenixCorneaE {
        const val commonUtil = "com.github.FPhoenixCorneaE:CommonUtil:1.0.6"
        const val commonTitlebar = "com.github.FPhoenixCorneaE:CommonTitlebar:1.0.4"
    }

    object GradlePlugin {
        const val gradle = "com.android.tools.build:gradle:4.1.3"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val androidMaven = "com.github.dcendents:android-maven-gradle-plugin:2.1"
    }

    object PluginIds {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val kotlinAndroid = "android"
        const val kotlinParcelize = "kotlin-parcelize"
        const val kotlinKapt = "kapt"
        const val androidMaven = "com.github.dcendents.android-maven"
    }

    object Versions {
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.3"
        const val minSdkVersion = 21
        const val targetSdkVersion = 30
        const val versionName = "1.0.0"
        const val versionCode = 100
        const val kotlinVersion = "1.4.32"
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
    object Coroutines{
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.3"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.3"
    }

    object AndroidX {
        const val appcompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.1.0"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val material = "com.google.android.material:material:1.2.1"
        const val flexBox = "com.google.android:flexbox:2.0.1"
        const val coreKtx = "androidx.core:core-ktx:1.3.1"
        const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
        const val fragmentKtx = "androidx.fragment:fragment-ktx:1.3.0-beta01"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val startup = "androidx.startup:startup-runtime:1.0.0"
    }

    object Lifecycle {
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    }

    object Navigation {
        const val runtimeKtx = "androidx.navigation:navigation-runtime-ktx:2.3.1"
        const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.3.0"
        const val uiKtx = "androidx.navigation:navigation-ui-ktx:2.3.0"
    }

    object Test {
        const val junit = "junit:junit:4.12"
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }

    object Retrofit2 {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
        // 动态替换 BaseUrl 库：https://github.com/JessYanCoding/RetrofitUrlManager
        const val urlManager = "me.jessyan:retrofit-url-manager:1.4.0"
    }

    object RxJava2 {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.0"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.0"
        const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.2.0"
    }

    object OkHttp3 {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.4.0"
        /** Cookies 自动持久化 */
        const val persistentCookieJar = "com.github.franmontiel:PersistentCookieJar:v1.0.1"
    }

    object ARouter {
        const val api = "com.alibaba:arouter-api:1.5.0"
        const val compiler = "com.alibaba:arouter-compiler:1.2.2"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:1.2.0"
        const val gif = "io.coil-kt:coil-gif:1.2.0"
        const val svg = "io.coil-kt:coil-svg:1.2.0"
        const val video = "io.coil-kt:coil-video:1.2.0"
        const val transformations = "com.github.Commit451.coil-transformations:transformations:1.0.0"
        const val transformationsGpu = "com.github.Commit451.coil-transformations:transformations-gpu:1.0.0"
        const val transformationsFaceDetection = "com.github.Commit451.coil-transformations:transformations-face-detection:1.0.0"
    }

    object DoraemonKit {
        const val debug = "com.didichuxing.doraemonkit:doraemonkit:3.2.0"
        const val release = "com.didichuxing.doraemonkit:doraemonkit-no-op:3.2.0"
    }

    object Pandora {
        const val debug = "com.github.whataa:pandora:androidx_v2.1.0"
        const val release = "com.github.yuanhoujun:pandora-no-op:v2.0.4"
    }

    object Detekt {
        const val formatting = "io.gitlab.arturbosch.detekt:detekt-formatting:1.14.0"
    }

    object Bugly {
        const val crashReport = "com.tencent.bugly:crashreport:3.2.3"
        const val nativeCrashReport = "com.tencent.bugly:nativecrashreport:3.7.47"
    }

    /** Powerful and flexible RecyclerAdapter */
    const val baseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"

    val unpeekLiveData = "com.kunminx.archi:unpeek-livedata:4.4.1-beta1"

    /** 优雅地处理加载中，重试，无数据等 */
    const val loadSir = "com.kingja.loadsir:loadsir:1.3.8"

    /** 提供从js调用Java代码和从java调用js代码的安全便捷方法。 */
    const val jsbridge = "com.github.lzyzsd:jsbridge:1.0.4"

    const val aliCloudHotfix = "com.aliyun.ams:alicloud-android-hotfix:3.2.15"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.7"


    /**
     * 当前时间
     */
    fun getSystemTime(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA)
        return simpleDateFormat.format(System.currentTimeMillis())
    }
}
