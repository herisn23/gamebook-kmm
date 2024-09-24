import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.cio.CIO
import io.ktor.server.engine.EngineConnectorBuilder
import io.ktor.server.engine.embeddedServer
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.util.ThreadInfo
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

fun main() {

    embeddedServer(CIO, configure = {
        connectors.add(EngineConnectorBuilder().apply {
            port = 8080
            host = "0.0.0.0"
        })
    }, module = {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        module()
    })
        .start(wait = true)
}

@Serializable
data class Response(
    val message: String
)

fun Application.module() {
    routing {
        get("/") {
            call.respond(Response("Hello World Ktor!"))
        }
    }
}