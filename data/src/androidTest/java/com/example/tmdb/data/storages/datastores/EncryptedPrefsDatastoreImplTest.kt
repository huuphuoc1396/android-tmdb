package com.example.tmdb.data.storages.datastores

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EncryptedPrefsDatastoreImplTest {
    private val testScope = TestScope()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val encryptedUserDatastoreImpl = EncryptedPrefsDatastoreImpl(context)
    private val TOKEN_TYPE = "Bear"
    private val TOKEN_BODY = "oDE6UUqAoPxKKIoSF3D75KZrzV4qwLssoDE6UUqAoPxKKIoSF3D7PxKKIoSF3D75KZrzV4qwLss"
    private val TEST_TOKEN = "$TOKEN_TYPE $TOKEN_BODY"
    private val LOGIN_RESULT = true

    @Test
    fun setLoggedInByResult() = testScope.runTest {
        encryptedUserDatastoreImpl.apply {
            setLoggedIn(LOGIN_RESULT)
            isLoggedIn.test {
                expectMostRecentItem() shouldBe LOGIN_RESULT
            }
        }
    }

    @Test
    fun setTokenType() = testScope.runTest {
        encryptedUserDatastoreImpl.apply {
            setTokenType(TOKEN_TYPE)
            tokenType.test {
                expectMostRecentItem() shouldBe TOKEN_TYPE
            }
        }
    }

    @Test
    fun setAccessToken() = testScope.runTest {
        encryptedUserDatastoreImpl.apply {
            setAccessToken(TEST_TOKEN)
            accessToken.test {
                expectMostRecentItem() shouldBe TEST_TOKEN
            }
        }
    }

    @Test
    fun refreshToken() = testScope.runTest {
        val charArrayBody = TOKEN_BODY.toCharArray()
        charArrayBody.shuffle()
        val newTokenBody = charArrayBody.joinToString("")
        val newToken = "$TOKEN_TYPE $newTokenBody"
        encryptedUserDatastoreImpl.apply {
            setAccessToken(newToken)
            accessToken.test {
                expectMostRecentItem() shouldBe newToken
            }
        }
    }

    @Test
    fun clearAll() = testScope.runTest {
        encryptedUserDatastoreImpl.apply {
            clearAll()
            isLoggedIn.test {
                expectMostRecentItem() shouldBe false
            }
            tokenType.test {
                expectMostRecentItem() shouldBe ""
            }
            accessToken.test {
                expectMostRecentItem() shouldBe ""
            }
            refreshToken.test {
                expectMostRecentItem() shouldBe ""
            }
        }
    }

}