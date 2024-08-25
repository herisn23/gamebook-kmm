plugins {
    alias(libs.plugins.kotlinJvm)
    alias(sl.plugins.micronaut.application)
    alias(sl.plugins.micronaut.aot)
    alias(sl.plugins.shadowjar)
}
dependencies {
    implementation(mn.micronaut.management)
    implementation(mn.micronaut.kotlin.runtime)
    implementation(mn.micronaut.micrometer.core)
    implementation(mn.micronaut.micrometer.registry.prometheus)
    implementation(mn.micronaut.reactor)
    implementation(mn.micrometer.context.propagation)

    implementation(mn.jackson.databind)

    //arm libs
    implementation(
        group = "io.netty",
        name = "netty-resolver-dns-native-macos",
        classifier = "osx-aarch_64"
    )


    //project modules
    implementation(projects.server.micronaut.app.web)
    implementation(projects.server.flyway)


    runtimeOnly(mn.micronaut.kubernetes.discovery.client)
    runtimeOnly(mn.micronaut.kubernetes.client.reactor)
    runtimeOnly(mn.logback.classic)
    runtimeOnly(mn.snakeyaml)
    runtimeOnly(mn.micronaut.reactor.http.client)
    runtimeOnly(sl.bundles.logback.contrib)

    testImplementation(mn.micronaut.test.junit5)
    testImplementation(mn.micronaut.http.client)
    testImplementation(mn.reactor.test)
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("cz.roldy.gb.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
        replaceLogbackXml = true
    }

    importMicronautPlatform = true
}

//application settings
application {
    mainClass = "cz.roldy.gb.ApplicationKt"
}
//
tasks.all {
    //optimizedJitJarAll pouziva shadowJar, ale zrejme si vytvari nejakou vlastni instanci
    //zkousel jsem to dat na shadowJar jak v tomto modulu, tak i v allProjects, ale bez uspechu a samotny task optimizedJitJarAll pri bootu gradlu neexistuje, takze to hned crashne
    //absolutne jsem tedy nepochopil jak pro task :boot:optimizedJitJarAll nastavit zip64 jinak nez timto zpusobem
    if (name.startsWith("optimizedJitJarAll")) {
        (this as Jar).isZip64 = true
    }
}
//
//// graalvm settings (not usable for now)
//graalvmNative.toolchainDetection = false
//
//tasks.dockerfileNative {
////    graalImage.set("ghcr.io/graalvm/native-image-community:22.0.2-muslib") //ghcr.io/graalvm/native-image-community:17-ol9
//}
//
//// ./gradlew dockerBuildNative
//// docker rm webforms-graal; docker run -e MICRONAUT_ENVIRONMENTS=graalvm-local -p 8080:8080 --name webforms-graal webforms-graal
//
//// docker build --no-cache . --tag webforms-jvm --file Dockerfile_aarch64
// docker rm webforms-jvm; docker run -e MICRONAUT_ENVIRONMENTS=graalvm-local -p 8080:8080 --name webforms-jvm webforms-jvm