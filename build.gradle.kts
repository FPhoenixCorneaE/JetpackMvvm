// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(Deps.GradlePlugin.gradle)
        classpath(Deps.GradlePlugin.kotlin)
        classpath(Deps.GradlePlugin.androidMaven)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            setUrl("https://jitpack.io")
        }
    }
}

task("clean", Delete::class) {
    delete = setOf(rootProject.buildDir)
}