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

    fun searchByName(name: String, limit: Int): List<Author> {
        return entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.name LIKE :name ESCAPE '\\'",
                Author::class.java
        )
                .setParameter("name", "${name.escapeLikeReservations()}%")
                .setMaxResults(limit)
                .resultList
    }

    fun findByIdIn(ids: List<Long>): List<Author> {
        // JPQLのINクエリに空のリストを渡すとエラーになるため先にチェックする
        if (ids.isEmpty()) {
            return listOf()
        }
        return entityManager.createQuery(
                "SELECT a FROM Author a WHERE a.id IN :ids",
                Author::class.java
        )
                .setParameter("ids", ids)
                .resultList
    }
}
