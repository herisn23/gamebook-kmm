import core.async.ExecutionScope
import core.async.onView
import core.cache.cache
import http.HttpApiClient
import http.StoryApiClient
import io.github.aakira.napier.Napier

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

suspend fun StoryApiClient.cachedImage(id: String, force: Boolean = false): ByteArray? =
    cache("image-story-$id", force = force) {
        image(id)
    }