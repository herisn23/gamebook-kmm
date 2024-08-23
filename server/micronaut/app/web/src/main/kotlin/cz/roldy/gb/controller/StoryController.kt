package cz.roldy.gb.controller

import cz.roldy.gb.story.model.YamlSource
import io.micronaut.core.io.ResourceResolver
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.listDirectoryEntries
import kotlin.io.path.name
import kotlin.jvm.optionals.getOrNull


@Controller("/story")
class StoryController(
    private val resourceResolver: ResourceResolver
) {

    @Get
    fun stories(): Mono<YamlSource> =
        getResource("stories.yaml").readContent().toYamlSource()

    @Get("/{id}")
    fun story(@PathVariable id: String): Mono<YamlSource> =
        getResource("${id}/api.yaml").readContent().toYamlSource()

    @Get("/image/{id}", produces = ["image/png"])
    fun image(@PathVariable id: String) =
        getResource("${id}/image.png").readContent()

    @Get("/{id}/localization")
    fun localizations(@PathVariable id: String): Flux<String> =
        getResourceEntries("$id/strings").map {
            it.name.replace(".yaml", "")
        }

    @Get("/{id}/localization/{lang}")
    fun localization(@PathVariable id: String, @PathVariable lang: String): Mono<YamlSource> =
        getResource("$id/strings/$lang.yaml").readContent().toYamlSource()


    private fun getResource(path: String): Mono<URL> =
        Mono.create { sink ->
            sink.success(resourceResolver.getResource("classpath:story/$path").getOrNull())
        }

    private fun getResourceEntries(path: String): Flux<Path> =
        Flux.create { sink ->
            resourceResolver.getResources("classpath:story/$path").forEach {
                Path.of(it.toURI()).run {
                    listDirectoryEntries().forEach(sink::next)
                }
            }
            sink.complete()
        }

    private fun Mono<URL>.readContent() =
        map {
            it.readBytes()
        }

    private fun Mono<ByteArray>.toYamlSource() =
        map {
            YamlSource(String(it))
        }
}


