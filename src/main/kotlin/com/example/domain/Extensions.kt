package com.example.domain

val likeReservationRegex = """([%_\\])""".toRegex()

fun String.escapeLikeReservations(): String = likeReservationRegex.replace(this, "\\\\$1")
