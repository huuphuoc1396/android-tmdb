package com.example.tmdb.di.modules

import com.example.tmdb.navigations.Navigator
import com.example.tmdb.navigations.NavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    @Singleton
    fun provideNavigator(impl: NavigatorImpl): Navigator
}