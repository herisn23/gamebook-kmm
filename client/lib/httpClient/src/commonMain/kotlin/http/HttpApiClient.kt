package http
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig

expect fun buildClient(block: HttpClientConfig<HttpClientEngineConfig>.() -> Unit): HttpClient


interface HttpApiClient


