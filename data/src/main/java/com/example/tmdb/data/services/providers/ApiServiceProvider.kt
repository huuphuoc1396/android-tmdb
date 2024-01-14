package com.example.tmdb.data.services.providers

import com.example.tmdb.data.services.AuthService
import com.example.tmdb.data.services.NonAuthService
import retrofit2.Retrofit

object ApiServiceProvider {

    init {
        System.loadLibrary("native-lib")
    }

    @JvmStatic
    external fun getApiKey(id: Int): String

    val key1: String
        get() = getApiKey(1)

    val key2: String
        get() = getApiKey(2)

    fun getNonAuthService(retrofit: Retrofit): NonAuthService {
        return retrofit.create(NonAuthService::class.java)
    }

    fun getAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }
}
