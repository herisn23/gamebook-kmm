import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.buildKonfig)
}

kotlin {

    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "gameHttpClient"
        browser()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(projects.shared)

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
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }

}

android {
    namespace = "cz.roldy.gb.httpClient"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

buildkonfig {
    packageName = "cz.roldy.gb.http"

    val apiUrlPropertyName = "API_URL"
    val httpLoggingEnabledPropName = "HTTP_LOGGING_ENABLED"

    val apiUrlLocalhostAndroid: String by project
    val apiUrlLocalhost: String by project
    val apiUrlStage: String by project
    val apiUrlRelease: String by project

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlRelease)
        buildConfigField(FieldSpec.Type.BOOLEAN, httpLoggingEnabledPropName, "false")
    }
    defaultConfigs("dev") {
        buildConfigField(FieldSpec.Type.BOOLEAN, httpLoggingEnabledPropName, "true")
        buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlLocalhost)
    }

    defaultConfigs("stage") {
        buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlStage)
        buildConfigField(FieldSpec.Type.BOOLEAN, httpLoggingEnabledPropName, "true")
    }
    targetConfigs("dev") {
        create("android") {
            buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlLocalhostAndroid)
        }
        create("iosSim") {
            buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlLocalhost)
        }
    }

}
