package cz

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
class HelloController {

    @Get("/")
    fun index(): String {
        return "Hello World Micronaut!"
    }
}