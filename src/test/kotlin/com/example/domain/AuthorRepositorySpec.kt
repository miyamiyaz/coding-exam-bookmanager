package com.example.domain

import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject
import javax.transaction.Transactional

@MicronautTest
open class AuthorRepositorySpec {

    @Inject
    lateinit var bookRepository: BookRepository

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Test
    fun testSearchByName_NotExist() {
        val authors = authorRepository.searchByName("伊坂幸太郎")

        Assertions.assertEquals(0, authors.size)
    }

    @Test
    fun testSearchByName_Empty() {
        val authors = authorRepository.searchByName("")

        Assertions.assertEquals(4, authors.size)
    }

    @Test
    fun testSearchByName_PerfectSingle() {
        val authors = authorRepository.searchByName("夏目漱石")

        Assertions.assertEquals(1, authors.size)
        Assertions.assertEquals("夏目漱石", authors[0].name)
    }

    @Test
    fun testSearchByName_PrefixSingle() {
        val authors = authorRepository.searchByName("正岡")

        Assertions.assertEquals(1, authors.size)
        Assertions.assertEquals("正岡子規", authors[0].name)
    }

    @Test
    fun testSearchByName_PrefixMultiple() {
        val authors = authorRepository.searchByName("Lewis")

        Assertions.assertEquals(2, authors.size)
        Assertions.assertEquals("Lewis Carroll", authors[0].name)
        Assertions.assertEquals("Lewis Ginter", authors[1].name)
    }

    @Transactional
    @BeforeEach
    open fun init() {
        authorRepository.saveAll(listOf(
                Author(name = "Lewis Carroll"),
                Author(name = "Lewis Ginter"),
                Author(name = "夏目漱石"),
                Author(name = "正岡子規"),
        ))
    }

    @Transactional
    @AfterEach
    open fun finalize() {
        bookRepository.deleteAll()
        authorRepository.deleteAll()
    }
}
