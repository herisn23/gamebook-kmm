package http

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.js.Js


actual fun buildClient(block: HttpClientConfig<HttpClientEngineConfig>.() -> Unit): HttpClient =
    HttpClient(Js, block)

