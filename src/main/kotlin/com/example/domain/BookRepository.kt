package com.example.domain

import io.micronaut.data.annotation.Query
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import javax.persistence.EntityManager


@Repository
abstract class BookRepository(private val entityManager: EntityManager) : CrudRepository<Book, Long> {
    @Query("SELECT b FROM Book b JOIN b.authors a WHERE a.id = :id")
    abstract fun findByAuthorId(id: Long): List<Book>

    @Query("SELECT COUNT(b.id) FROM Book b JOIN b.authors a WHERE a.id = :id")
    abstract fun countByAuthorId(id: Long): Long

    fun searchByTitle(title: String): List<Book> {
        return entityManager.createQuery(
                "SELECT b FROM Book b WHERE b.title LIKE :title ESCAPE '\\'",
                Book::class.java
        )
                .setParameter("title", "${title.escapeLikeReservations()}%")
                .resultList
    }

    fun searchByAuthor(name: String): List<Book> {
        return entityManager.createQuery(
                "SELECT b FROM Book b JOIN b.authors a WHERE a.name LIKE :name ESCAPE '\\'",
                Book::class.java
        )
                .setParameter("name", "${name.escapeLikeReservations()}%")
                .resultList
    }
}
