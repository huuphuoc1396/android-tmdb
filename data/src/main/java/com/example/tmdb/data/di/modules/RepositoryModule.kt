package com.example.tmdb.data.di.modules

import com.example.tmdb.data.repositories.UserRepositoryImpl
import com.example.tmdb.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideUserRepository(impl: UserRepositoryImpl): UserRepository
}