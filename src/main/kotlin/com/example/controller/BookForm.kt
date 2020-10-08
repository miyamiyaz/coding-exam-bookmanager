package com.example.controller

import io.micronaut.core.annotation.Introspected
import io.micronaut.core.convert.format.Format
import java.time.LocalDateTime
import javax.validation.constraints.*


@Introspected
open class BookForm {
    var id: Long? = null

    @NotBlank
    @Size(max = 255)
    var title: String? = null

    @Format("yyyy-MM-dd'T'HH:mm")
    @NotNull
    @Future
    var publishAt: LocalDateTime? = null

    @NotEmpty
    var authorIds: MutableList<Long> = mutableListOf()

    override fun toString(): String {
        return "BookForm(id=$id, title=$title, publishAt=$publishAt, authorIds=$authorIds)"
    }
}
