package http

import cz.roldy.gb.config.BuildKonfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json

val defaultClient = buildClient {
    defaultRequest {
        url(BuildKonfig.API_URL)
        header("X-PLATFORM", BuildKonfig.PLATFORM)
    }

    if (BuildKonfig.HTTP_LOGGING_ENABLED)
        install(Logging)

    install(Resources)
    install(ContentNegotiation) {
        json()

    }
}