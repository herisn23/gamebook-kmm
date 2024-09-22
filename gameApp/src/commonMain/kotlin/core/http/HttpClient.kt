package core.http

import cz.roldy.gb.config.BuildKonfig
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import platform

val defaultClient = buildClient {
    defaultRequest {
        url(BuildKonfig.API_URL)
        header("X-PLATFORM", platform.name)
        header("Access-Control-Allow-Origin", "*")
    }

    if (BuildKonfig.HTTP_LOGGING_ENABLED)
        install(Logging)

    install(Resources)
    install(ContentNegotiation) {
        json()

    }
}

/**
 * Returns body only when match with status code
 */
suspend inline fun <reified T> HttpResponse.body(statusCode: HttpStatusCode) =
    when (status) {
        statusCode -> body<T>()
        else -> null
    }
