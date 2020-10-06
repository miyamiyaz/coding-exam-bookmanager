package com.example.domain

import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository
import javax.persistence.EntityManager

@Repository
abstract class AuthorRepository(private val entityManager: EntityManager) : CrudRepository<Author, Long> {
    fun searchByName(name: String): List<Author> {
        return entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.name LIKE :name ESCAPE '\\'",
                Author::class.java
        )
                .setParameter("name", "${name.escapeLikeReservations()}%")
                .resultList
    }
}
