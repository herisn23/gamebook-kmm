package http

import cache.cache
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode


class StoryApiClient(private val client: HttpClient) : HttpApiClient {
    suspend fun stories(): String =
        client.get("/stories.yaml").body()

    suspend fun strings(id: String, lang: String): String =
        client.get("/story/$id/strings/$lang.yaml").body()

    suspend fun api(id: String, api: String): String =
        client.get("/story/$id/$api").body()

    suspend fun image(id: String): ByteArray? =
        client.get("/story/$id/image.png") {
            accept(ContentType.Image.PNG)
        }.body(HttpStatusCode.OK)
}

val sac: StoryApiClient = StoryApiClient(defaultClient)

suspend fun StoryApiClient.cachedImage(id: String, force: Boolean = false): ByteArray? =
    cache("image-story-$id", force = force) {
        image(id)
    }