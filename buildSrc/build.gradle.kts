import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}


repositories {
    mavenCentral()
    mavenLocal()
    google()
}

gradlePlugin {
    plugins {
        register("module-plugin") {
            id = "module-plugin"
            implementationClass = "plugins.CommonModulePlugin"
        }

        register("feature-plugin") {
            id = "feature-plugin"
            implementationClass = "plugins.FeatureModulePlugin"
        }
    }
}
