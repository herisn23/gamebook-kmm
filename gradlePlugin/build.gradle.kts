plugins {
    `kotlin-dsl`
}
dependencies {
    implementation(libs.kotlin.gradle.plugin)
}



gradlePlugin {
    // Define the plugin
    plugins {
        create("native-target") {
            id = "native-target"
            implementationClass = "gradle.NativeTargetPlugin"
        }
    }
}