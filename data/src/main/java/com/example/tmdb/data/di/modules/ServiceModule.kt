package com.example.tmdb.data.di.modules

import com.example.tmdb.data.BuildConfig
import com.example.tmdb.data.di.annotations.AuthClient
import com.example.tmdb.data.di.annotations.NonAuthClient
import com.example.tmdb.data.services.AuthService
import com.example.tmdb.data.services.NonAuthService
import com.example.tmdb.data.services.providers.ApiServiceProvider
import com.example.tmdb.data.services.providers.CallAdapterFactoryProvider
import com.example.tmdb.data.services.providers.ConverterFactoryProvider
import com.example.tmdb.data.services.providers.RetrofitProvider
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideBaseApiUrl() = BuildConfig.BASE_API_URL

    @Provides
    fun provideMoshiConverterFactory(
        moshi: Moshi
    ): Converter.Factory = ConverterFactoryProvider.getMoshiConverterFactory(moshi)

    @Provides
    fun provideMappingApiErrorCallAdapterFactory(): CallAdapter.Factory =
        CallAdapterFactoryProvider.getMappingApiErrorCallAdapterFactory()

    @Provides
    @NonAuthClient
    fun provideNonAuthRetrofit(
        baseUrl: String,
        @NonAuthClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(
            baseUrl = baseUrl,
            okHttpClient = okHttpClient,
            converterFactory = converterFactory,
            callAdapterFactory = callAdapterFactory,
        )
        .build()

    @Provides
    fun provideNonAuthService(
        @NonAuthClient retrofit: Retrofit
    ): NonAuthService = ApiServiceProvider.getNonAuthService(retrofit)

    @Provides
    @AuthClient
    fun provideAuthRetrofit(
        baseUrl: String,
        @AuthClient okHttpClient: OkHttpClient,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
    ): Retrofit = RetrofitProvider
        .getRetrofitBuilder(
            baseUrl = baseUrl,
            okHttpClient = okHttpClient,
            converterFactory = converterFactory,
            callAdapterFactory = callAdapterFactory,
        )
        .build()

    @Provides
    fun provideAuthService(
        @AuthClient retrofit: Retrofit
    ): AuthService = ApiServiceProvider.getAuthService(retrofit)
}