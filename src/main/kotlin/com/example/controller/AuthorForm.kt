package com.example.controller

import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotBlank


@Introspected
open class AuthorForm {
    var id: Long? = null

    @NotBlank
    var name: String? = null
}
