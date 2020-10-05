package com.example.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.validation.constraints.NotBlank


@Entity(name = "author")
data class Author(
        @get:NotBlank
        @Column(name = "name")
        var name: String = "",

        @ManyToMany(mappedBy = "authors")
        val books: Set<Book> = HashSet()
) : BaseEntity()
