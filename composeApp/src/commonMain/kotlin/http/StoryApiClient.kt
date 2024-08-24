package http

import cache.cache
import cz.roldy.gb.story.model.YamlSource
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode


class StoryApiClient(private val client: HttpClient) : HttpApiClient {
    suspend fun stories(): YamlSource =
        client.get("/story").body()

    suspend fun localizations(id: String): List<String> =
        client.get("/story/$id/localization").body()

    suspend fun localizations(id: String, lang: String): YamlSource =
        client.get("/story/$id/localization/$lang").body()

    suspend fun api(id: String): YamlSource =
        client.get("/story/$id").body()

    suspend fun image(id: String): ByteArray? =
        client.get("/story/image/$id") {
            accept(ContentType.Image.PNG)
        }.body(HttpStatusCode.OK)
}

val sac: StoryApiClient = StoryApiClient(defaultClient)

suspend fun StoryApiClient.cachedImage(id: String, force: Boolean = false): ByteArray? =
    cache("image-story-$id", force = force) {
        image(id)
    }