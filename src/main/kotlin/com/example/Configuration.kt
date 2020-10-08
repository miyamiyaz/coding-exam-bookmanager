package com.example

import com.example.domain.Author
import com.example.domain.AuthorRepository
import com.example.domain.Book
import com.example.domain.BookRepository
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent
import org.thymeleaf.TemplateEngine
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect
import javax.inject.Inject
import javax.inject.Singleton
import javax.transaction.Transactional


@Singleton
open class Configuration : ApplicationEventListener<ServerStartupEvent> {

    @Inject
    lateinit var templateEngine: TemplateEngine

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var bookRepository: BookRepository

    override fun onApplicationEvent(event: ServerStartupEvent) {
        // Add #temporals dialect to thymeleaf template engine
        templateEngine.addDialect(Java8TimeDialect())
        // Add fixtures to db
        registerInitialData()
    }

    @Transactional
    open fun registerInitialData() {
        println("=== Data initializing...")
        val louisCarol = authorRepository.save(Author(name = "Lewis Carroll"))
        bookRepository.save(Book(title = "Alice in wonderland", authors = setOf(louisCarol)))
        bookRepository.save(Book(title = "The Hunting of the Snark", authors = setOf(louisCarol)))
        val souseki = authorRepository.save(Author(name = "夏目漱石"))
        bookRepository.save(Book(title = "それから", authors = setOf(souseki)))
        bookRepository.save(Book(title = "吾輩は猫である", authors = setOf(souseki)))
        val shiki = authorRepository.save(Author(name = "正岡子規"))
        bookRepository.save(Book(title = "漱石・子規往復書簡集", authors = setOf(souseki, shiki)))

        for (book in bookRepository.searchByAuthor("夏目")) {
            println("${book.id}: $book")
        }
    }
}
