plugins {
    alias(libs.plugins.kotlinJvm)
    alias(sl.plugins.kotlin.allopen)
    alias(sl.plugins.kotlin.kapt)
}


val mnRef = project.mn
val slRef = project.sl
val libsRef = project.libs
val projectsRef = projects


//allprojects {
//    apply(plugin = slRef.plugins.kotlin.kapt.get().pluginId)
//
//    dependencies {
//        kapt(mnRef.micronaut.validation.processor)
//        kapt(mnRef.micronaut.serde.processor)
//        kapt(mnRef.micronaut.http.validation)
//    }
//}


subprojects {
    apply(plugin = slRef.plugins.kotlin.allopen.get().pluginId)

    allOpen {
        annotation("jakarta.inject.Singleton")
        annotation("io.micronaut.http.annotation.Controller")
        annotation("io.micronaut.validation.Validated")
    }
    dependencies {
//        kapt(mnRef.micronaut.validation.processor)
//        kapt(mnRef.micronaut.serde.processor)
//        kapt(mnRef.micronaut.http.validation)

        implementation(mnRef.micronaut.serde.jackson)
        implementation(mnRef.micronaut.kotlin.extension.functions)
        implementation(mnRef.jackson.module.kotlin)
        implementation(libsRef.kotlinx.serialization.json)
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("io.projectreactor.addons:reactor-extra")

        implementation(projectsRef.shared)

        //test libs
        testImplementation(mnRef.junit.jupiter.api)
        testImplementation(mnRef.junit.jupiter.params)
        testImplementation(mnRef.junit.jupiter.engine)
        testImplementation(mnRef.reactor.test)
        testImplementation(slRef.mockk)
    }
}