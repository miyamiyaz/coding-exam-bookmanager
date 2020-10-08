package com.example.controller

import com.example.domain.Author
import com.example.domain.AuthorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.*
import io.micronaut.validation.Validated
import io.micronaut.views.View
import java.net.URI
import javax.inject.Inject
import javax.transaction.Transactional
import javax.validation.Valid


@Validated
@Controller("/author")
class AuthorController {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Transactional
    @Get("/")
    @View("author/index")
    fun index(@QueryValue("q") query: String?): HttpResponse<Map<String, Any>> {
        val authors = query?.let { authorRepository.searchByName(it) } ?: authorRepository.findAll().toList()

        return HttpResponse.ok(mapOf("authors" to authors, "query" to (query ?: "")))
    }

    @Transactional
    @Get("/edit")
    @View("author/edit")
    fun edit(@QueryValue("id") id: Long?): HttpResponse<Map<String, Any?>> {
        val author = if (id != null) {
            authorRepository.findById(id).orElse(null) ?: return HttpResponse.notFound()
        } else {
            Author()
        }

        return HttpResponse.ok(mapOf("author" to author, "id" to id))
    }

    @Transactional
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON)
    @Post("/edit")
    fun save(@Body @Valid authorForm: AuthorForm): HttpResponse<String> {
        val id = authorForm.id
        val author = if (id != null) {
            authorRepository.findById(id).orElse(null) ?: return HttpResponse.notFound("author not found")
        } else {
            Author()
        }

        authorRepository.save(author.apply {
            name = authorForm.name ?: ""
        })
        return HttpResponse.redirect(URI("/author"))
    }

    @Transactional
    @Delete("/{id}")
    fun delete(@PathVariable id: Long): HttpResponse<String> {
        authorRepository.deleteById(id)
        return HttpResponse.redirect(URI("/author"))
    }
}
