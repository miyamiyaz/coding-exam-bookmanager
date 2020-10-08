package com.example.controller

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.convert.format.Format
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Introspected
open class BookForm {
    var id: Long? = null

    @NotBlank
    @Size(max = 255)
    var title: String? = null

    @Format("yyyy-MM-dd'T'HH:mm")
    @NotNull
    var publishAt: LocalDateTime? = null

    @NotEmpty
    var authorIds: MutableList<Long> = mutableListOf()

    override fun toString(): String {
        return "BookForm(id=$id, title=$title, publishAt=$publishAt, authorIds=$authorIds)"
    }
}
