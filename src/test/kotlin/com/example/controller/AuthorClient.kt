package com.example.controller

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single


@Client("/author")
interface AuthorClient {
    @Get(consumes = [MediaType.TEXT_PLAIN])
    fun index(): Single<String>
}
