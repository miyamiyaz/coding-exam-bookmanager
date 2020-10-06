package com.example.domain

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
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
) : BaseEntity()
