package com.example.controller

import com.example.domain.BookRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.views.View
import javax.inject.Inject

@Controller("/book")
class BookController {

    @Inject
    lateinit var bookRepository: BookRepository

    @Get("/")
    @View("book/index")
    fun index(@QueryValue("q") query: String?): HttpResponse<Map<String, Any>> {
        val books = query?.let { bookRepository.searchByTitle(it) } ?: bookRepository.findAll()

        return HttpResponse.ok(mapOf("books" to books))
    }
}
