package plugins

import Dependencies
import Ids
import Kapt
import org.gradle.api.Project
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.dependencies


fun Project.addHilt() {
    plugins.apply(Ids.hilt)
    dependencies {
        implementation(Dependencies.hilt)
        kapt(Kapt.hilt)
    }
}

fun DependencyHandlerScope.implementation(dependencyNotation: Any) {
    add("implementation", dependencyNotation)
}

fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) {
    add("testImplementation", dependencyNotation)
}

fun DependencyHandlerScope.androidTestImplementation(dependencyNotation: Any) {
    add("androidTestImplementation", dependencyNotation)
}


fun DependencyHandlerScope.kapt(dependencyNotation: Any) {
    add("kapt", dependencyNotation)
}








