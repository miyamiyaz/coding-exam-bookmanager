package com.example.domain

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*


@MappedSuperclass
open class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @field:CreationTimestamp
    @Column(name = "created_at", updatable = false)
    lateinit var createdAt: LocalDateTime

    @field:UpdateTimestamp
    @Column(name = "updated_at")
    lateinit var updatedAt: LocalDateTime

    @Version
    @Column(name = "version")
    val version: Long = 0
}
