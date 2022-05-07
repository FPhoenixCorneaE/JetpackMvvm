plugins {

    /**
     * You should use `apply false` in the top-level build.gradle file
     * to add a Gradle plugin as a build dependency, but not apply it to the
     * current (root) project. You should not use `apply false` in sub-projects.
     * For more information, see
     * Applying external plugins with same version to subprojects.
     */

    id(Deps.PluginId.application) version Deps.Version.agpVersion apply false
    id(Deps.PluginId.library) version Deps.Version.agpVersion apply false
    id(Deps.PluginId.kotlin) version Deps.Version.kotlinVersion apply false
}

task(name = "clean", type = Delete::class) {
    delete = setOf(rootProject.buildDir)
}