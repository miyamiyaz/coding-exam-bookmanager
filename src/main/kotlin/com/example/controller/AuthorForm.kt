package com.example.controller

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size


@Introspected
open class AuthorForm {
    var id: Long? = null

    @Size(max = 255)
    @NotBlank
    var name: String? = null
}
