plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.jetbrainsCompose) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.kotlinJvm) apply false
    alias(sl.plugins.kotlin.kapt) apply false
    alias(sl.plugins.micronaut.application) apply false
    alias(sl.plugins.micronaut.aot) apply false
    alias(sl.plugins.micronaut.library) apply false
    alias(sl.plugins.shadowjar) apply false
    alias(sl.plugins.kover) apply false
    alias(sl.plugins.sonarqube) apply false
    alias(sl.plugins.kotlin.allopen) apply false
    alias(libs.plugins.buildKonfig) apply false
}