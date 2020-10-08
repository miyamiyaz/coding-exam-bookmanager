package com.example.controller

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single


@Client("/author")
interface AuthorClient {
    @Get("/")
    fun index(@QueryValue q: String? = null): Single<HttpResponse<String>>

    @Get("/edit")
    fun edit(@QueryValue id: Long? = null): Single<HttpResponse<String>>

    @Post("/edit")
    fun save(name: String, id: Long? = null): Single<HttpResponse<String>>

    @Post("/delete")
    fun delete(id: Long): Single<HttpResponse<String>>
}
