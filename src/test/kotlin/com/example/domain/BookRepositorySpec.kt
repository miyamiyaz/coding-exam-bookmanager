package com.example.domain

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.transaction.Transactional

@MicronautTest
open class BookRepositorySpec {

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Inject
    lateinit var bookRepository: BookRepository

    @Test
    fun testFindByAuthorId() {
        val authors = authorRepository.searchByName("正岡子規")
        Assertions.assertEquals(1, authors.size)
        val books = bookRepository.findByAuthorId(authors[0].id)

        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals("漱石・子規往復書簡集", books[0].title)
    }

    @Test
    fun testSearchByTitle_NotExist() {
        val books = bookRepository.searchByTitle("グラスホッパー")

        Assertions.assertEquals(0, books.size)
    }

    @Test
    fun testSearchByTitle_Empty() {
        val books = bookRepository.searchByTitle("")

        Assertions.assertEquals(5, books.size)
    }

    @Test
    fun testSearchByTitle_Perfect() {
        val books = bookRepository.searchByTitle("漱石・子規往復書簡集")

        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals("漱石・子規往復書簡集", books[0].title)
    }

    @Test
    fun testSearchByTitle_Prefix() {
        val books = bookRepository.searchByTitle("吾輩")

        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals("吾輩は猫である", books[0].title)
    }

    @Test
    fun testSearchByAuthor_Empty() {
        val books = bookRepository.searchByAuthor("")

        Assertions.assertEquals(6, books.size)
    }

    @Test
    fun testSearchByAuthor_Perfect() {
        val books = bookRepository.searchByAuthor("正岡子規")

        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals("漱石・子規往復書簡集", books[0].title)
    }

    @Test
    fun testSearchByAuthor_Prefix() {
        val books = bookRepository.searchByAuthor("夏目")

        Assertions.assertEquals(3, books.size)
        Assertions.assertEquals("それから", books[0].title)
        Assertions.assertEquals("吾輩は猫である", books[1].title)
        Assertions.assertEquals("漱石・子規往復書簡集", books[2].title)
    }

    @Transactional
    @BeforeEach
    open fun init() {
        val louisCarol = authorRepository.save(Author(name = "Lewis Carroll"))
        bookRepository.save(Book(title = "Alice in wonderland", authors = setOf(louisCarol)))
        bookRepository.save(Book(title = "The Hunting of the Snark", authors = setOf(louisCarol)))
        val souseki = authorRepository.save(Author(name = "夏目漱石"))
        bookRepository.save(Book(title = "それから", authors = setOf(souseki)))
        bookRepository.save(Book(title = "吾輩は猫である", authors = setOf(souseki)))
        val shiki = authorRepository.save(Author(name = "正岡子規"))
        bookRepository.save(Book(title = "漱石・子規往復書簡集", authors = setOf(souseki, shiki)))
    }

    @Transactional
    @AfterEach
    open fun finalize() {
        bookRepository.deleteAll()
        authorRepository.deleteAll()
    }
}
