package com.example.tmdb.domain.interactors

interface UseCase<in P, out R> {
    suspend fun execute(params: P): R
}

typealias Empty = Unit