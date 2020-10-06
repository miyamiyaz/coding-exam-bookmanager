package com.example.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.views.View

@Controller("/book")
class BookController {

    @View("book/index")
    @Get("/")
    fun index(): HttpResponse<Map<String, Any>> {
        return HttpResponse.ok(mapOf("loggedIn" to true, "username" to "sdelamo"))
    }
}
