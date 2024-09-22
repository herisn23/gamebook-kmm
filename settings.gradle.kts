rootProject.name = "gamebook"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/kotlin/p/wasm/experimental")
    }
    versionCatalogs {
        create("mn") {
            from("io.micronaut.platform:micronaut-platform:4.5.0")
        }
        create("sl") {
            from(files("./gradle/server-libs.versions.toml"))
        }
    }
}

include(":shared")

include(":gameHttpClient")
include(":gameCore")
include(":gameApp")
include(":editorApp")


include(":server")
include(":server:flyway")
include(":server:micronaut")
include(":server:micronaut:boot")
include(":server:micronaut:app")
include(":server:micronaut:app:web")
