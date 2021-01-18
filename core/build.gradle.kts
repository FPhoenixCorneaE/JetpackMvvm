plugins {
    id(Deps.PluginIds.library)
    kotlin(Deps.PluginIds.kotlinAndroid)
    kotlin(Deps.PluginIds.kotlinExtensions)
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
        getByName("release") {
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
        getByName("debug") {
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
        isAbortOnError = false
    }

    androidExtensions {
        isExperimental = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.Kotlin.stdLibJdk7)
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.lifecycleRuntimeKtx)
    implementation(Deps.FPhoenixCorneaE.commonUtil)
}

// 添加以下配置，否则上传后的jar包看不到注释-------------------------------------------------------------

// 指定编码
//tasks.withType(JavaCompile::class) {
//    options.encoding = "UTF-8"
//}
//// 打包源码
//task("sourcesJar", Jar::class) {
//    if (project.hasProperty("kotlin")) {
//        sourceSets {
//            val main by getting
//            from((main.java as com.android.build.gradle.api.AndroidSourceDirectorySet).srcDirs)
//        }
//    }
//    classifier = "sources"
//}
//task("javadoc", Javadoc::class) {
//    isFailOnError = false
//    if (project.hasProperty("android")) {
//        sourceSets {
//            val main by getting
//            source =
//                (main.java as com.android.build.gradle.api.AndroidSourceDirectorySet).sourceFiles
//        }
//    }
//    classpath += project.files(android.bootClasspath.joinToString(File.pathSeparator))
//    classpath += configurations.compile
//}
//// 制作文档(Javadoc)
//task("javadocJar", Jar::class) {
//    classifier = "javadoc"
//    val javadoc = tasks.getByName("javadoc") as Javadoc
//    from(javadoc.destinationDir)
//}
//artifacts {
//    val sourcesJar = tasks.getByName("sourcesJar")
//    val javadocJar = tasks.getByName("javadocJar")
//    archives(sourcesJar)
//    archives(javadocJar)
//}