plugins {
    alias(libs.plugins.kotlinJvm)
    alias(sl.plugins.kotlin.kapt)
}

dependencies {
    kapt(mn.micronaut.validation.processor)
    kapt(mn.micronaut.serde.processor)
    kapt(mn.micronaut.http.validation)
}