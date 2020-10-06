package com.example.controller

import com.example.domain.AuthorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.views.View
import javax.inject.Inject
import javax.transaction.Transactional


@Controller("/author")
open class AuthorController {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Transactional
    @Get("/")
    @View("author/index")
    open fun index(@QueryValue("q") query: String?): HttpResponse<Map<String, Any>> {
        val authors = query?.let { authorRepository.searchByName(it) } ?: authorRepository.findAll().toList()

        return HttpResponse.ok(mapOf("authors" to authors, "query" to (query ?: "")))
    }

    @Transactional
    @Post("/register")
    open fun register() {

    }
}
