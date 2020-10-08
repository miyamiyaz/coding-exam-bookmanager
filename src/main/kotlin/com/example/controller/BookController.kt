package com.example.controller

import com.example.domain.AuthorRepository
import com.example.domain.Book
import com.example.domain.BookRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import io.micronaut.views.View
import java.net.URI
import java.time.LocalDateTime
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid


@Validated
@Controller("/book")
class BookController {

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Transactional
    @Get("/")
    @View("book/index")
    fun index(
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

    @Transactional
    @Get("/edit")
    @View("book/edit")
    fun edit(@QueryValue("id") id: Long?): HttpResponse<Map<String, Any?>> {
        val book = if (id != null) {
            bookRepository.findById(id).orElse(null) ?: return HttpResponse.notFound()
        } else {
            Book()
        }

        return HttpResponse.ok(mapOf("book" to book, "id" to id))
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON)
    @Post("/edit")
    fun save(@Body @Valid bookForm: BookForm): HttpResponse<String> {
        val id = bookForm.id
        val book = if (id != null) {
            bookRepository.findById(id).orElse(null) ?: return HttpResponse.notFound("book not found")
        } else {
            Book()
        }
        val authors = authorRepository.findByIdIn(bookForm.authorIds)
        book.authors.addAll(authors)
        println(bookForm)
        println(book)
        when {
            book.authors.isEmpty() ->
                return HttpResponse.badRequest("authorIds must not be empty.")
            bookForm.publishAt == null ->
                return HttpResponse.badRequest("publishAt must not be empty.")
            bookForm.publishAt?.isBefore(LocalDateTime.now()) == true ->
                return HttpResponse.badRequest("publishAt must be future datetime.")
        }
        println("AAA")

        bookRepository.save(book.apply {
            title = bookForm.title ?: ""
            publishAt = bookForm.publishAt!!
        })
        return HttpResponse.redirect(URI("/book"))
    }

    @Transactional
    @Delete("/{id}")
    fun delete(@PathVariable id: Long): HttpResponse<String> {
        bookRepository.deleteById(id)
        return HttpResponse.redirect(URI("/book"))
    }

}
