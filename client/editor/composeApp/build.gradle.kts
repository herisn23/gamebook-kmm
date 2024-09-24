import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "gamebookEditor"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "gamebookEditor.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    port = 3001
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            implementation(projects.shared)
            implementation(projects.client.lib.core)


            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.jetbrains.navigation)
            implementation(libs.aakira.napier)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.serialization.yaml)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.plugins.client.resources)
            implementation(libs.ktor.plugins.client.negotiation)
            implementation(libs.ktor.plugins.negotiation.json)
            implementation(libs.ktor.plugins.client.logging)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}


