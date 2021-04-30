plugins {
    id(Deps.PluginIds.library)
    kotlin(Deps.PluginIds.kotlinAndroid)
    kotlin(Deps.PluginIds.kotlinKapt)
    id(Deps.PluginIds.androidMaven)
}
group = "com.github.FPhoenixCorneaE"

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
            // Zipalign优化
            isZipAlignEnabled = true
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
            // Zipalign优化
            isZipAlignEnabled = true
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
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // kotlin
    compileOnly(Deps.Kotlin.stdLib)
    compileOnly(Deps.AndroidX.coreKtx)
    compileOnly(Deps.AndroidX.appcompat)
    // lifecycle
    api(Deps.Lifecycle.runtimeKtx)
    api(Deps.Lifecycle.extensions)
    // liveData
    api(Deps.Lifecycle.liveDataKtx)
    api(Deps.unpeekLiveData)
    // viewModel
    api(Deps.Lifecycle.viewModelKtx)
    // navigation
    api(Deps.Navigation.runtimeKtx)
    api(Deps.Navigation.fragmentKtx)
    api(Deps.Navigation.uiKtx)
    // startup
    api(Deps.Startup.runtime)
    // 通用工具类
    api(Deps.FPhoenixCorneaE.commonUtil)
    // 标题栏
    api(Deps.FPhoenixCorneaE.commonTitlebar)
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
}

// 添加以下配置，否则上传后的jar包看不到注释-------------------------------------------------------------

// 指定编码
tasks.withType(JavaCompile::class) {
    options.encoding = "UTF-8"
}
// 打包源码
task("sourcesJar", Jar::class) {
    from("src/main/kotlin")
    classifier = "sources"
}
task("javadoc", Javadoc::class) {
    isFailOnError = false
    source = fileTree(mapOf("dir" to "src/main/kotlin", "include" to listOf("*.*")))
    classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
    classpath += configurations.compile
}
// 制作文档(Javadoc)
task("javadocJar", Jar::class) {
    classifier = "javadoc"
    val javadoc = tasks.getByName("javadoc") as Javadoc
    from(javadoc.destinationDir)
}
artifacts {
    val sourcesJar = tasks.getByName("sourcesJar")
    val javadocJar = tasks.getByName("javadocJar")
    archives(sourcesJar)
    archives(javadocJar)
}