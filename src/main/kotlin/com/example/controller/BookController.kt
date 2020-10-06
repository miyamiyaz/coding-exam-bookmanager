package com.example.controller

import com.example.domain.BookRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.views.View
import javax.inject.Inject
import javax.transaction.Transactional


@Controller("/book")
open class BookController {

    @Inject
    lateinit var bookRepository: BookRepository

    @Transactional
    @Get("/")
    @View("book/index")
    open fun index(
            @QueryValue("q") query: String?,
            @QueryValue("a") authorId: Long?
    ): HttpResponse<Map<String, Any>> {
        val books = when {
            authorId != null -> bookRepository.findByAuthorId(authorId)
            query != null -> bookRepository.searchByTitle(query)
            else -> bookRepository.findAll().toList()
        }

        return HttpResponse.ok(mapOf("books" to books, "query" to (query ?: "")))
    }
}
