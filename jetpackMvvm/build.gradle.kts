plugins {
    id(Deps.PluginId.library)
    id(Deps.PluginId.kotlin)
    kotlin(Deps.PluginId.kotlinKapt)
    `maven-publish`
}

android {
    defaultConfig {
        buildToolsVersion = Deps.DefaultConfig.buildToolsVersion
        compileSdk = Deps.DefaultConfig.compileSdk
        minSdk = Deps.DefaultConfig.minSdk
        targetSdk = Deps.DefaultConfig.targetSdk
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
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    configurations.all {
        resolutionStrategy {
            force(Deps.AndroidX.appcompat)
            force(Deps.AndroidX.recyclerView)
            force(Deps.AndroidX.activityKtx)
            force(Deps.AndroidX.fragmentKtx)
            force(Deps.AndroidX.coreKtx)
        }
    }
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // androidX
    compileOnly(Deps.AndroidX.appcompat)
    compileOnly(Deps.AndroidX.coreKtx)
    compileOnly(Deps.AndroidX.viewpager2)
    api(Deps.AndroidX.multiDex)
    api(Deps.AndroidX.activityKtx)
    api(Deps.AndroidX.fragmentKtx)
    // lifecycle
    api(Deps.Lifecycle.runtimeKtx)
    api(Deps.Lifecycle.commonJava8)
    api(Deps.Lifecycle.liveDataKtx)
    api(Deps.Lifecycle.viewModelKtx)
    api(Deps.Lifecycle.viewModelSavedState)
    api(Deps.Lifecycle.process)
    api(Deps.Lifecycle.service)
    kapt(Deps.Lifecycle.compiler)
    // navigation
    api(Deps.Navigation.runtimeKtx)
    api(Deps.Navigation.commonKtx)
    api(Deps.Navigation.fragmentKtx)
    api(Deps.Navigation.uiKtx)
    // startup
    api(Deps.Startup.runtime)
    // 通用工具类
    api(Deps.FPhoenixCorneaE.common)
    // 协程挂起网络请求
    api(Deps.FPhoenixCorneaE.coRetrofit)
    // 标题栏
    api(Deps.FPhoenixCorneaE.commonToolbar)
    // 状态布局管理
    api(Deps.FPhoenixCorneaE.loadSir)
    // coil 图片加载
    api(Deps.Coil.coil)
    api(Deps.Coil.gif)
    api(Deps.Coil.svg)
    api(Deps.Coil.video)
    api(Deps.CoilTransformations.transformations)
    api(Deps.CoilTransformations.transformationsGpu)
    api(Deps.CoilTransformations.transformationsFaceDetection)
    // 数据存储
    api(Deps.mmkv)
}

// MavenPublication 配置 start -------------------------------------------------------------
afterEvaluate {
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
}
// MavenPublication 配置 end ---------------------------------------------------------------
