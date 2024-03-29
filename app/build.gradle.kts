plugins {
    id(Deps.PluginId.application)
    id(Deps.PluginId.kotlin)
}

android {
    defaultConfig {
        applicationId = Deps.DefaultConfig.applicationId
        buildToolsVersion = Deps.DefaultConfig.buildToolsVersion
        compileSdk = Deps.DefaultConfig.compileSdk
        minSdk = Deps.DefaultConfig.minSdk
        targetSdk = Deps.DefaultConfig.targetSdk
        versionCode = Deps.DefaultConfig.versionCode
        versionName = Deps.DefaultConfig.versionName
        testInstrumentationRunner = Deps.DefaultConfig.testInstrumentationRunner
        multiDexEnabled = true
        ndk {
            // 设置支持的SO库架构
            abiFilters.addAll(listOf("armeabi-v7a", "x86"))  //'armeabi', 'x86', 'armeabi-v7a', 'x86_64', 'arm64-v8a'
        }
    }

    buildTypes {
        getByName(Deps.BuildType.Release) {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    // 资源重定向
    sourceSets {
        getByName("main") {
            res.srcDir(listSubFile())
        }
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

    packagingOptions {
        resources.excludes.add("META-INF/proguard/coroutines.pro")
    }

    configurations.all {
        resolutionStrategy {
        }
    }
}

// 输出文件
android.applicationVariants.all {
    // 编译类型
    val buildType = buildType.name
    outputs.all {
        // 输出 Apk
        if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
            if (buildType == Deps.BuildType.Debug) {
                this.outputFileName =
                    "${project.name}_V${android.defaultConfig.versionName}_${buildType}_${Deps.getSystemTime()}.apk"
            } else if (buildType == Deps.BuildType.Release) {
                this.outputFileName =
                    "${project.name}_V${android.defaultConfig.versionName}_${buildType}_${Deps.getSystemTime()}.apk"
            }
        }
    }
}

// 部署资源文件
fun listSubFile(): ArrayList<String> {
    // 新资源目录
    val resFolder = "src/main/res/layouts"
    // 新资源目录下的文件夹
    val files = file(resFolder).listFiles()
    val folders = ArrayList<String>()
    // 遍历路径
    files?.forEach { file ->
        folders.add(file.absolutePath)
    }
    // 资源整合
    folders.add(file(resFolder).parentFile.absolutePath)
    return folders
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Deps.AndroidX.coreKtx)
    implementation(Deps.AndroidX.appcompat)
    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.material)
    implementation(Deps.AndroidX.recyclerView)
    debugImplementation(Deps.leakcanary)
    implementation(project(mapOf("path" to ":jetpackMvvm")))
    //    implementation(Deps.FPhoenixCorneaE.jetpackMvvm)
    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.junitExt)
    androidTestImplementation(Deps.Test.junitExtKtx)
    androidTestImplementation(Deps.Test.espresso)
}