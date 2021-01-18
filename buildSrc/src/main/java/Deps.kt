/**
 * @desc：所有依赖库按照规范写在这里，用到的地方，通过Deps引用即可。在添加依赖之前，请检查该文件中是否已存在相
 *        同的依赖，如若已经添加相同依赖，则无需再重复添加，请注意。
 * @date：2021/1/16 19:08
 */
object Deps {

    object FPhoenixCorneaE {
        const val commonUtil = "com.github.FPhoenixCorneaE:CommonUtil:1.0.4"
    }

    object GradlePlugin {
        const val gradle = "com.android.tools.build:gradle:4.0.1"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlinVersion}"
        const val androidMaven = "com.github.dcendents:android-maven-gradle-plugin:2.1"
    }

    object Versions {
        const val compileSdkVersion = 30
        const val buildToolsVersion = "30.0.2"
        const val minSdkVersion = 24
        const val targetSdkVersion = 30
        const val versionName = "1.0.0"
        const val versionCode = 100
        const val kotlinVersion = "1.4.0"
    }

    object Kotlin {
        const val stdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
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
        const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
        const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        const val cardView = "androidx.cardview:cardview:1.0.0"
        const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
        const val startup = "androidx.startup:startup-runtime:1.0.0"
    }

    object Test {
        const val junit = "junit:junit:4.12";
        const val junitExt = "androidx.test.ext:junit:1.1.2"
        const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
    }

    object Retrofit2 {
        const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
        const val converterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    }

    object OkHttp3 {
        const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:3.4.0"
    }

    object ARouter {
        const val api = "com.alibaba:arouter-api:1.5.0"
        const val compiler = "com.alibaba:arouter-compiler:1.2.2"
    }

    object Navigation {
        const val navFragment = "androidx.navigation:navigation-fragment-ktx:2.3.0"
        const val navUi = "androidx.navigation:navigation-ui-ktx:2.3.0"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:4.11.0"
        const val okHttp3Integration = "com.github.bumptech.glide:okhttp3-integration:4.11.0"
        const val compiler = "com.github.bumptech.glide:compiler:4.11.0"
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

    const val baseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:2.9.46"

    // 提供从js调用Java代码和从java调用js代码的安全便捷方法。
    const val jsbridge = "com.github.lzyzsd:jsbridge:1.0.4"

    const val aliCloudHotfix = "com.aliyun.ams:alicloud-android-hotfix:3.2.15"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.5"
}

object Versions {
    const val compileSdkVersion = 30
    const val buildToolsVersion = "30.0.2"
    const val minSdkVersion = 24
    const val targetSdkVersion = 30
    const val versionName = "1.0.0"
    const val versionCode = 100
}

object Kotlin {
    const val kotlinVersion = "1.4.0"
    const val gradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${kotlinVersion}"
    const val stdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${kotlinVersion}"
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
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.2.0"
    const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
    const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
    const val cardView = "androidx.cardview:cardview:1.0.0"
    const val swipeRefreshLayout = "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    const val startup = "androidx.startup:startup-runtime:1.0.0"
}