package http

import cz.roldy.gb.story.model.YamlSource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get


class StoryApiClient(private val client: HttpClient) : HttpApiClient {
    suspend fun stories(): YamlSource =
        client.get("/story").body()

    suspend fun localizations(id: String): List<String> =
        client.get("/story/$id/localization").body()

    suspend fun localizations(id: String, lang: String): YamlSource =
        client.get("/story/$id/localization/$lang").body()

    suspend fun api(id: String): YamlSource =
        client.get("/story/$id").body()
}

val storyApiClient: StoryApiClient = StoryApiClient(defaultClient)