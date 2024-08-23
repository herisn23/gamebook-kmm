group = "cz.roldy.gb.server"
version = "1.0.0"

plugins {
    alias(libs.plugins.kotlinJvm) apply false
    alias(sl.plugins.micronaut.application) apply false
    alias(sl.plugins.micronaut.aot) apply false
    alias(sl.plugins.kotlin.kapt) apply false
    alias(sl.plugins.micronaut.library)
    alias(sl.plugins.shadowjar)
    alias(sl.plugins.kover)
    alias(sl.plugins.sonarqube)
    alias(sl.plugins.kotlin.allopen)
}
val slRef = sl
val libsRef = libs

dependencies {
    compileOnly(projects.server.micronaut.boot)
    compileOnly(projects.server.flyway)
}

allprojects {
    apply(plugin = slRef.plugins.shadowjar.get().pluginId)
    apply(plugin = slRef.plugins.kover.get().pluginId)
    apply(plugin = slRef.plugins.micronaut.library.get().pluginId)

    tasks.koverXmlReport {
        enabled = true
    }

    tasks.test {
        useJUnitPlatform()
    }

    tasks.shadowJar {
        mergeServiceFiles()
        isZip64 = true
    }
}