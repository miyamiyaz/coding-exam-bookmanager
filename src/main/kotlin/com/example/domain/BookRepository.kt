package com.example.domain

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface BookRepository : CrudRepository<Book, Long> {
    @Query("SELECT b FROM book b JOIN b.authors a WHERE a.name = :name")
    fun findByAuthor(name: String): List<Book>
}
