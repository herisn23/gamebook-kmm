@file:OptIn(BetaInteropApi::class, ExperimentalForeignApi::class)

package cache

import io.github.aakira.napier.Napier
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.readBytes
import kotlinx.cinterop.toCValues
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.temporaryDirectory
import platform.Foundation.writeToURL

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
actual suspend fun save(fileName: String, bytes: ByteArray): ByteArray? =
    getFileUrl(fileName)?.let { file ->
        Napier.i { "file exist $file" }
        val nsData = bytes.toPointer()
        if (nsData?.writeToURL(file, true) == true) {
            bytes
        } else {
            null
        }
    }

actual suspend fun load(fileName: String): ByteArray? =
    getFileUrl(fileName)?.let { file ->
        NSData.create(file)?.let { data ->
            data.bytes?.readBytes(data.length.toInt())
        }
    }

actual suspend fun exist(fileName: String): Boolean =
    getFileUrl(fileName)?.let { file->
        NSFileManager.defaultManager.fileExistsAtPath(file.path!!)
    } ?: false

private fun ByteArray?.toPointer(): NSData? =
    this?.run {
        val pointer = toCValues().getPointer(MemScope())
        NSData.create(bytes = pointer, length = size.toULong())
    }

private fun getFileUrl(fileName: String): NSURL? =
    NSFileManager.defaultManager.temporaryDirectory().URLByAppendingPathComponent(fileName)