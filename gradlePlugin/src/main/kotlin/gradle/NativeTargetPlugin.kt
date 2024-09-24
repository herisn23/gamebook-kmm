package gradle

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.add
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

fun KotlinMultiplatformExtension.nativeTarget(name: String? = null) =
    run {
        val hostOs = System.getProperty("os.name")
        val isArm64 = System.getProperty("os.arch") == "aarch64"
        val isMingwX64 = hostOs.startsWith("Windows")
        when {
            hostOs == "Mac OS X" && isArm64 -> macosArm64(name ?: "macosArm64") {}
            hostOs == "Mac OS X" && !isArm64 -> macosX64(name ?: "macosX64") {}
            hostOs == "Linux" && isArm64 -> linuxArm64(name ?: "linuxArm64") {}
            hostOs == "Linux" && !isArm64 -> linuxX64(name ?: "linuxX64") {}
            isMingwX64 -> mingwX64(name ?: "mingwX64")
            else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
        }
    }

fun KotlinMultiplatformExtension.nativeTarget(
    name: String = "native",
    block: KotlinNativeTarget.() -> Unit
) {
    nativeTarget(name).block()
}
class NativeTargetPlugin : Plugin<Project> {
    override fun apply(project: Project) {

    }
}
