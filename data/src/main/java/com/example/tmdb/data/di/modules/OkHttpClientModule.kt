package com.example.tmdb.data.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.tmdb.data.di.annotations.AuthClient
import com.example.tmdb.data.di.annotations.NonAuthClient
import com.example.tmdb.data.services.interceptors.AuthInterceptor
import com.example.tmdb.data.services.interceptors.HeaderInterceptor
import com.example.tmdb.data.services.interceptors.TokenAuthenticator
import com.example.tmdb.data.services.providers.OkHttpClientProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor

@Module
@InstallIn(SingletonComponent::class)
class OkHttpClientModule {

    @Provides
    @NonAuthClient
    fun provideNonAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        headerInterceptor: HeaderInterceptor,
    ) = OkHttpClientProvider.getOkHttpClientBuilder(
        httpLoggingInterceptor = httpLoggingInterceptor,
        chuckerInterceptor = chuckerInterceptor,
        headerInterceptor = headerInterceptor,
    ).build()

    @Provides
    @AuthClient
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        chuckerInterceptor: ChuckerInterceptor,
        headerInterceptor: HeaderInterceptor,
        authInterceptor: AuthInterceptor,
        tokenAuthenticator: TokenAuthenticator,
    ) = OkHttpClientProvider.getOkHttpClientBuilder(
        httpLoggingInterceptor = httpLoggingInterceptor,
        chuckerInterceptor = chuckerInterceptor,
        headerInterceptor = headerInterceptor,
        authInterceptor = authInterceptor,
        tokenAuthenticator = tokenAuthenticator,
    ).build()

    @Provides
    fun provideChuckerInterceptor(
        @ApplicationContext context: Context
    ): ChuckerInterceptor = OkHttpClientProvider.getChuckerInterceptor(context)

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor =
        OkHttpClientProvider.getHttpLoggingInterceptor()

    @Provides
    fun provideHeaderInterceptor(): HeaderInterceptor =
        OkHttpClientProvider.getHeaderInterceptor()

}