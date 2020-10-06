package com.example.domain

import javax.persistence.*
import javax.validation.constraints.NotBlank


@Entity
@Table(
        name = "author",
        indexes = [
            Index(name = "idx_author__name", columnList = "name", unique = false)
        ]
)
data class Author(
        @get:NotBlank
        @Column(name = "name")
        var name: String = "",

        @ManyToMany(mappedBy = "authors")
        val books: Set<Book> = HashSet()
) : BaseEntity()
