package com.example.controller

import com.example.domain.Author
import com.example.domain.AuthorRepository
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject
import javax.transaction.Transactional


@Controller("/author")
open class AuthorController {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Transactional
    @Get(produces = [MediaType.TEXT_PLAIN])
    open fun index(): String {
        var author = authorRepository.save(Author(name = "AAAAA"))
        println("${author.createdAt} ${author.updatedAt} ${author.version}:: $author")
        author.name = "BBBBB"
        author = authorRepository.save(author)
        println("${author.createdAt} ${author.updatedAt} ${author.version}:: $author")
        return "Hello World"
    }
}
