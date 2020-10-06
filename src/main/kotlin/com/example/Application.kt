package com.example

import com.example.domain.Author
import com.example.domain.AuthorRepository
import com.example.domain.Book
import com.example.domain.BookRepository
import io.micronaut.runtime.Micronaut.build
import io.micronaut.runtime.event.annotation.EventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("com.example")
            .start()
}

@Singleton
open class DataInitializer {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var bookRepository: BookRepository

    @Transactional
    @EventListener
    open fun onStartup(event: ServerStartupEvent) {
        println("=== Data initializing...")
        val louisCarol = authorRepository.save(Author(name = "Lewis Carroll"))
        bookRepository.save(Book(title = "Alice in wonderland", authors = setOf(louisCarol)))
        bookRepository.save(Book(title = "The Hunting of the Snark", authors = setOf(louisCarol)))
        val souseki = authorRepository.save(Author(name = "夏目漱石"))
        bookRepository.save(Book(title = "それから", authors = setOf(souseki)))
        bookRepository.save(Book(title = "吾輩は猫である", authors = setOf(souseki)))
        val shiki = authorRepository.save(Author(name = "正岡子規"))
        bookRepository.save(Book(title = "漱石・子規往復書簡集", authors = setOf(souseki, shiki)))

        println(bookRepository.searchByAuthor("夏目"))
    }
}
