package com.example.controller

import com.example.domain.Author
import com.example.domain.AuthorRepository
import com.example.domain.Book
import com.example.domain.BookRepository
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import javax.inject.Inject
import javax.transaction.Transactional

@MicronautTest
open class BookControllerSpec {

    @Inject
    lateinit var bookClient: BookClient

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Test
    fun testIndex_Get() {
        val response = bookClient.index().blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertTrue(response.body()!!.contains("書籍一覧"))
    }

    @Test
    fun testEdit_GetNew() {
        val response = bookClient.edit().blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertTrue(response.body()!!.contains("書籍情報編集"))
    }

    @Test
    fun testEdit_GetEdit() {
        val book = bookRepository.searchByTitle("梟の城").let {
            Assertions.assertEquals(1, it.size)
            it[0]
        }

        val response = bookClient.edit(book.id).blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertTrue(response.body()!!.contains("梟の城"))
    }

    @Test
    @Transactional
    open fun testSave_New() {
        val author = authorRepository.searchByName("東野圭吾").let {
            Assertions.assertEquals(1, it.size)
            it[0]
        }
        val response = bookClient.save(
                "真夏の方程式",
                LocalDateTime.now().plusDays(1),
                listOf(author.id)
        ).blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)

        val books = bookRepository.searchByTitle("真夏の方程式")
        Assertions.assertEquals(1, books.size)
        Assertions.assertEquals(1, books[0].authors.size)
        Assertions.assertEquals("東野圭吾", books[0].authors.toList()[0].name)
    }

    @Test
    open fun testDelete_One() {
        val book = bookRepository.searchByTitle("半沢直樹").let {
            Assertions.assertEquals(1, it.size)
            it[0]
        }

        Assertions.assertEquals(HttpStatus.OK, bookClient.delete(book.id).blockingGet().status)
        val searchResults = bookRepository.searchByTitle("半沢直樹")
        Assertions.assertEquals(0, searchResults.size)
    }

    @Transactional
    @BeforeEach
    open fun init() {
        val shiba = Author(name = "司馬遼太郎")
        val ikei = Author(name = "池井戸潤")
        authorRepository.saveAll(listOf(
                shiba,
                Author(name = "東野圭吾"),
                ikei,
        ))
        bookRepository.saveAll(listOf(
                Book(title = "梟の城", authors = mutableSetOf(shiba)),
                Book(title = "半沢直樹", authors = mutableSetOf(ikei)),
        ))
    }

    @Transactional
    @AfterEach
    open fun finalize() {
        bookRepository.deleteAll()
        authorRepository.deleteAll()
    }
}

