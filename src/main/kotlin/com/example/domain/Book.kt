package com.example.domain

import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull


@Entity(name = "book")
data class Book(
        @get:NotNull
        @Column(name = "title")
        var title: String = "",

        @get:NotNull
        @Column(name = "publish_at")
        var publishAt: LocalDateTime = LocalDateTime.now(),

        @ManyToMany
        @JoinTable(
                name = "book_author_link",
                joinColumns = [JoinColumn(name = "author_id")],
                inverseJoinColumns = [JoinColumn(name = "book_id")]
        )
        val authors: Set<Author> = HashSet()
) : BaseEntity()
