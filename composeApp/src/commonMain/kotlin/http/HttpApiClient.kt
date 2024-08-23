package http

import async.ExecutionScope
import async.onView
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig

expect fun buildClient(block: HttpClientConfig<HttpClientEngineConfig>.() -> Unit): HttpClient


interface HttpApiClient

operator fun <API : HttpApiClient, T> API.invoke(
    apiCall: suspend API.() -> T,
    onError: (Throwable) -> Unit = {},
    onSuccess: (T) -> Unit
) = invoke(::onView, apiCall, onError, onSuccess)

operator fun <API : HttpApiClient, T> API.invoke(
    execution: ExecutionScope<T>,
    apiCall: suspend API.() -> T,
    onError: (Throwable) -> Unit,
    onSuccess: (T) -> Unit
) {
    execution(
        {
            apiCall()
        },
        onSuccess,
        {
            Napier.e(it) { "Error occurred when calling api ${this::class}" }
            onError(it)
        }
    )
}


