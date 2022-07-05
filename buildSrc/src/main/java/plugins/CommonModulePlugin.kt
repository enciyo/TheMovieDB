package plugins

import Dependencies
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project

class CommonModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply("kotlin-kapt")
        target.dependencies {
            implementation(Dependencies.coroutine)
            implementation(Dependencies.moshi)
            implementation(Dependencies.arrow)
            implementation(project(":shared"))
        }
        target.addHilt()
    }
}