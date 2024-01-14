package com.example.tmdb.data.services.interceptors

import com.example.tmdb.data.storages.datastores.EncryptedPrefsDatastore
import com.example.tmdb.domain.extensions.defaultEmpty
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", getAuthorization())
            .build()
        return chain.proceed(request)
    }

    private fun getAuthorization() = runBlocking {
        val tokenType = encryptedPrefsDatastore.tokenType.firstOrNull().defaultEmpty()
        val accessToken = encryptedPrefsDatastore.accessToken.firstOrNull().defaultEmpty()
        return@runBlocking "$tokenType $accessToken"
    }
}