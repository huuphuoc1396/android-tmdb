package com.example.tmdb.navigations

open class NoArgumentsDestination(route: String) : Destination(route) {
    operator fun invoke(): String = route
}