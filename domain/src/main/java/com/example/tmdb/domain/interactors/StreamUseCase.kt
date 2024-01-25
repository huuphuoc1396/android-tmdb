package com.example.tmdb.domain.interactors

import com.example.tmdb.domain.functional.safeFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

abstract class StreamUseCase<in P, out R>(
    private val dispatcher: CoroutineDispatcher,
) : UseCase<P, Flow<R>> {

    suspend operator fun invoke(params: P): Flow<Result<R>> {
        return execute(params).safeFlow(dispatcher)
    }
}