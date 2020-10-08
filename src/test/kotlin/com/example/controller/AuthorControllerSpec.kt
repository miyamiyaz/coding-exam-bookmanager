package com.example.controller

import com.example.domain.AuthorRepository
import io.micronaut.http.HttpStatus
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
open class AuthorControllerSpec {

    @Inject
    lateinit var authorClient: AuthorClient

    @Inject
    lateinit var authorRepository: AuthorRepository

    @Test
    fun testIndex_Get() {
        val response = authorClient.index().blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertTrue(response.body()!!.contains("著者一覧"))
    }

    @Test
    fun testEdit_GetNew() {
        val response = authorClient.edit().blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertTrue(response.body()!!.contains("著者編集"))
    }

    @Test
    fun testEdit_GetEdit() {
        authorClient.save("西尾維新").blockingGet()
        val author = authorRepository.searchByName("西尾維新").let {
            Assertions.assertEquals(1, it.size)
            it[0]
        }

        val response = authorClient.edit(author.id).blockingGet()
        Assertions.assertEquals(HttpStatus.OK, response.status)
        Assertions.assertTrue(response.body()!!.contains("西尾維新"))
    }

    @Test
    open fun testSave_New() {
        authorClient.save("芥川龍之介").blockingGet()
        val searchResults = authorRepository.searchByName("芥川龍之介")

        Assertions.assertEquals(1, searchResults.size)
        Assertions.assertEquals("芥川龍之介", searchResults[0].name)
    }

    @Test
    open fun testDelete_One() {
        authorClient.save("村上春樹").blockingGet()
        val author = authorRepository.searchByName("村上春樹").let {
            Assertions.assertEquals(1, it.size)
            it[0]
        }

        Assertions.assertEquals(HttpStatus.OK, authorClient.delete(author.id).blockingGet().status)
        val searchResults = authorRepository.searchByName("村上春樹")
        Assertions.assertEquals(0, searchResults.size)

    }
}
