package cache

import cz.roldy.gb.context
import java.nio.file.Path
import kotlin.io.path.exists
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes

actual suspend fun exist(fileName: String): Boolean =
    cacheDir().resolve(Path.of(fileName)).exists()

actual suspend fun save(fileName: String, bytes: ByteArray?): ByteArray? =
    bytes?.let {
        cacheDir().resolve(Path.of(fileName)).writeBytes(bytes).let { bytes }
    }


actual suspend fun load(fileName: String): ByteArray? =
    cacheDir().resolve(Path.of(fileName)).readBytes()

private fun cacheDir(): Path =
    context().cacheDir.toPath()