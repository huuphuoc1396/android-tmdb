package com.example.tmdb.navigations

open class NoArgumentsDestination(name: String) : Destination(name) {
    operator fun invoke(): String = name
}