package com.example.tmdb.data.storages.datastores

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import app.cash.turbine.test
import com.example.tmdb.domain.models.user.UserModel
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Test

class EncryptedUserDatastoreImplTest {
    private val testScope = TestScope()
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val encryptedUserDatastoreImpl = EncryptedUserDatastoreImpl(
        context = context,
    )

    @Test
    fun saveUser() = testScope.runTest {
        val testUser = UserModel(id = "1", name = "name 1", email = "email1@yahoo.com", avatarUrl = "http://www.google.com")
        encryptedUserDatastoreImpl.apply {
            saveUser(testUser)
            getUser().test {
                expectMostRecentItem() shouldBe testUser
            }
        }
    }

    @Test
    fun clearAll() = testScope.runTest {
        encryptedUserDatastoreImpl.apply {
            clearAll()
            getUser().test {
                expectMostRecentItem() shouldBe UserModel(id = "", name = "", email = "", avatarUrl = "")
            }
        }
    }
}