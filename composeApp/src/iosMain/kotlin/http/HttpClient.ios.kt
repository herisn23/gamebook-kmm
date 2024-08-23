package http

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.darwin.Darwin
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.MemScope
import kotlinx.cinterop.cstr
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.toCPointer
import kotlinx.cinterop.toCValues
import platform.Foundation.NSData
import platform.Foundation.NSFileManager
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.create
import platform.Foundation.dataWithBytes
import platform.Foundation.temporaryDirectory

actual fun buildClient(block: HttpClientConfig<HttpClientEngineConfig>.() -> Unit): HttpClient =
    HttpClient(Darwin, block)