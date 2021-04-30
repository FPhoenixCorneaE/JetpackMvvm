plugins {
    id(Deps.PluginIds.library)
    kotlin(Deps.PluginIds.kotlinAndroid)
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
}

dependencies {
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    compileOnly(Deps.Kotlin.stdLib)
    compileOnly(Deps.AndroidX.coreKtx)
    compileOnly(Deps.AndroidX.appcompat)
    compileOnly(Deps.Coroutines.core)
    compileOnly(Deps.Coroutines.android)
    compileOnly(Deps.FPhoenixCorneaE.commonUtil)
    api(Deps.Retrofit2.retrofit)
    api(Deps.Retrofit2.converterGson)
    api(Deps.Retrofit2.urlManager)
    api(Deps.OkHttp3.loggingInterceptor)
    api(Deps.OkHttp3.persistentCookieJar)
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