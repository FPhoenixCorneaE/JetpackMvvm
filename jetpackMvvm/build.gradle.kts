plugins {
    id(Deps.PluginIds.library)
    kotlin(Deps.PluginIds.kotlinAndroid)
    kotlin(Deps.PluginIds.kotlinKapt)
    id(Deps.PluginIds.mavenPublish)
}

android {
    compileSdkVersion(Deps.Versions.compileSdkVersion)
    buildToolsVersion(Deps.Versions.buildToolsVersion)

    defaultConfig {
        minSdkVersion(Deps.Versions.minSdkVersion)
        targetSdkVersion(Deps.Versions.targetSdkVersion)
        versionCode = Deps.Versions.versionCode
        versionName = Deps.Versions.versionName

        setConsumerProguardFiles(listOf("consumer-rules.pro"))
    }

    buildTypes {
        getByName(Deps.BuildType.Release) {
            // 执行proguard混淆
            isMinifyEnabled = false
            // 移除无用的resource文件
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName(Deps.BuildType.Debug) {
            // 执行proguard混淆
            isMinifyEnabled = false
            // 移除无用的resource文件
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    sourceSets {
        val main by getting
        main.java.srcDirs("src/main/kotlin")
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
        isCheckReleaseBuilds = false
        isAbortOnError = false
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    configurations.all {
        resolutionStrategy {
            force(Deps.Kotlin.stdLib)
            force(Deps.AndroidX.appcompat)
        }
    }
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // kotlin
    compileOnly(Deps.Kotlin.stdLib)
    compileOnly(Deps.AndroidX.coreKtx)
    compileOnly(Deps.AndroidX.appcompat)
    // lifecycle
    api(Deps.Lifecycle.runtimeKtx)
    api(Deps.Lifecycle.commonJava8)
    api(Deps.Lifecycle.extensions)
    // liveData
    api(Deps.Lifecycle.liveDataKtx)
    // viewModel
    api(Deps.Lifecycle.viewModelKtx)
    // navigation
    api(Deps.Navigation.runtimeKtx)
    api(Deps.Navigation.commonKtx)
    api(Deps.Navigation.fragmentKtx)
    api(Deps.Navigation.uiKtx)
    // startup
    api(Deps.Startup.runtime)
    // 通用工具类
    api(Deps.FPhoenixCorneaE.commonUtil)
    // 标题栏
    api(Deps.FPhoenixCorneaE.commonTitlebar) {
        exclude(group = "com.github.FPhoenixCorneaE", module = "CommonUtil")
    }
    // RecyclerViewAdapter
    api(Deps.baseRecyclerViewAdapterHelper) {
        exclude(group = "org.jetbrains.kotlin")
    }
    // coil 图片加载
    api(Deps.Coil.coil)
    api(Deps.Coil.gif)
    api(Deps.Coil.svg)
    api(Deps.Coil.video)
    api(Deps.Coil.transformations)
    api(Deps.Coil.transformationsGpu)
    api(Deps.Coil.transformationsFaceDetection)
    // 数据存储
    api(Deps.DataStore.mmkv)
    // logger
    api(Deps.Log.logger)
}

// MavenPublication 配置-------------------------------------------------------------

publishing {
    publications {
        // Creates a Maven publication called "release".
        create<MavenPublication>(Deps.BuildType.Release) {
            from(components[Deps.BuildType.Release])
            groupId = "com.github.FPhoenixCorneaE"
            artifactId = project.name
            version = project.version.toString()
        }
    }
}