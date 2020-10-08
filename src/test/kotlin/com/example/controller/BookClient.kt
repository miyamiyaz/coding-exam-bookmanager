package com.example.controller

import io.micronaut.core.convert.format.Format
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.client.annotation.Client
import io.reactivex.Single
import java.time.LocalDateTime


@Client("/book")
interface BookClient {
    @Get("/")
    fun index(@QueryValue q: String? = null, @QueryValue a: Long? = null): Single<HttpResponse<String>>

    @Get("/edit")
    fun edit(@QueryValue id: Long? = null): Single<HttpResponse<String>>

    @Post("/edit")
    fun save(
            title: String,
            @Format("yyyy-MM-dd'T'HH:mm") publishAt: LocalDateTime,
            authorIds: List<Long>,
            id: Long? = null
    ): Single<HttpResponse<String>>

    @Delete("/{id}")
    fun delete(id: Long): Single<HttpResponse<String>>
}
