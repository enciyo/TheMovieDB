package plugins

import Dependencies
import Ids
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


class FeatureModulePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.plugins.apply(Ids.hilt)
        target.plugins.apply(Ids.navigationComponent)
        target.dependencies {
            implementation(Dependencies.coreKtx)
            implementation(Dependencies.appCompat)
            implementation(Dependencies.material)
            implementation(Dependencies.constraintLayout)
            implementation(Dependencies.navigationComponent)
            implementation(Dependencies.navigationComponentUi)
            implementation(Dependencies.viewBindingPropertyDelegate)
        }
    }
}


