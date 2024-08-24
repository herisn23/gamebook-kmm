import com.codingfeline.buildkonfig.compiler.FieldSpec
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.moko.resources)
    alias(libs.plugins.buildKonfig)
}


kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
//        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            freeCompilerArgs += listOf("-Xbinary=bundleId=cz.roldy.gb")
        }
    }
    apply(plugin = "dev.icerock.mobile.multiplatform-resources")
    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
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
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.core)
            implementation(libs.moko.resources)
            implementation(libs.moko.resources.compose)
            implementation(libs.markdown.renderer)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.plugins.resources)
            implementation(libs.ktor.plugins.negotiation)
            implementation(libs.ktor.plugins.negotiation.json)
            implementation(libs.ktor.plugins.logging)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.ios)
        }
    }

    multiplatformResources {
        resourcesPackage.set("cz.roldy.gb") // required
//       resourcesClassName.set("SharedRes") // optional, default MR
//        resourcesVisibility.set(MRVisibility.Internal) // optional, default Public
        iosBaseLocalizationRegion.set("cs") // optional, default "en"
        iosMinimalDeploymentTarget.set("13.0") // optional, default "9.0"
    }
}

android {
    namespace = "cz.roldy.gb"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "cz.roldy.gb"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}


buildkonfig {
    packageName = "cz.roldy.gb.config"

    val apiUrlPropertyName = "API_URL"
    val httpLoggingEnabled = "HTTP_LOGGING_ENABLED"

    val apiUrlAndroidSimDev: String by project
    val apiUrlIosSimDev: String by project

    val apiUrlStage: String by project
    val apiUrlRelease: String by project

    defaultConfigs {
        buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlRelease)
        buildConfigField(FieldSpec.Type.BOOLEAN, httpLoggingEnabled, "false")
    }

    defaultConfigs("dev") {
        buildConfigField(FieldSpec.Type.BOOLEAN, httpLoggingEnabled, "true")
    }

    defaultConfigs("stage") {
        buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlStage)
        buildConfigField(FieldSpec.Type.BOOLEAN, httpLoggingEnabled, "true")
    }
    targetConfigs("dev") {
        create("android") {
            buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlAndroidSimDev)
        }
        create("ios") {
            buildConfigField(FieldSpec.Type.STRING, apiUrlPropertyName, apiUrlIosSimDev)
        }
    }
}