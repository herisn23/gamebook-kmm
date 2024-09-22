import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
//    if(project.hasProperty("editor"))
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "editorApp"
        browser {
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "editorApp.js"
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
            implementation(projects.gameCore)


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
            implementation(libs.ktor.plugins.resources)
            implementation(libs.ktor.plugins.negotiation)
            implementation(libs.ktor.plugins.negotiation.json)
            implementation(libs.ktor.plugins.logging)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}


