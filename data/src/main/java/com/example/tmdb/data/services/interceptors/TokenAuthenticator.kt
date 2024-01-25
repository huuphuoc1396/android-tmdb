package com.example.tmdb.data.services.interceptors

import com.example.tmdb.data.services.NoAuthService
import com.example.tmdb.data.services.requests.RefreshTokenRequest
import com.example.tmdb.data.storages.datastores.EncryptedPrefsDatastore
import com.example.tmdb.domain.extensions.defaultEmpty
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val noAuthService: NoAuthService,
    private val encryptedPrefsDatastore: EncryptedPrefsDatastore,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request {
        val newToken = refreshToken()
        return response.request.newBuilder()
            .header("Authorization", newToken)
            .build()
    }

    private fun refreshToken(): String = runBlocking {
        val refreshToken = encryptedPrefsDatastore
            .refreshToken
            .firstOrNull() ?: return@runBlocking ""
        val request = RefreshTokenRequest(refreshToken)
        val data = try {
            noAuthService.refreshToken(request).data
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
        if (data != null) {
            val tokenType = data.tokenType.defaultEmpty()
            val accessToken = data.accessToken.defaultEmpty()
            encryptedPrefsDatastore.setTokenType(tokenType)
            encryptedPrefsDatastore.setAccessToken(accessToken)
            encryptedPrefsDatastore.setRefreshToken(data.refreshToken.defaultEmpty())
            encryptedPrefsDatastore.setLoggedIn(true)
            return@runBlocking "$tokenType $accessToken"
        }
        encryptedPrefsDatastore.setLoggedIn(false)
        return@runBlocking ""
    }
}