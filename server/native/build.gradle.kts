import gradle.nativeTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinSerialization)
    id("native-target")
}

group = "cz.roldy.gb.server"
version = "1.0.0"

repositories {
    mavenCentral()
}

kotlin {
    nativeTarget("native") {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }
    sourceSets {
        commonMain.dependencies {

        }
        nativeMain.dependencies {
            implementation(libs.kotlinx.serialization.json)

            implementation(libs.ktor.server.core)
            implementation(libs.ktor.server.cio)
            implementation(libs.ktor.plugins.server.negotiation)
            implementation(libs.ktor.plugins.negotiation.json)
        }
    }
}
