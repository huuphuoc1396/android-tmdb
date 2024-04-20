package com.example.tmdb.data.repositories

import com.example.tmdb.data.services.AuthService
import com.example.tmdb.data.services.responses.DataResponse
import com.example.tmdb.data.services.responses.user.UserResponse
import com.example.tmdb.data.services.responses.user.toUserModel
import com.example.tmdb.data.storages.datastores.EncryptedUserDatastore
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class UserRepositoryImplTest {
    private val authService: AuthService = mockk()
    private val encryptedUserDatastore: EncryptedUserDatastore = mockk()
    private val userRepositoryImpl = UserRepositoryImpl(authService,encryptedUserDatastore)
    private val testUser = UserResponse(id = "1", name = "NAME 1", email = "email1@yahoo.com", avatarUrl = "http://www.google.com")


    @Test
    fun `fetch and save user successful`() = runTest {
        coEvery {
            authService.getUser()
        } returns DataResponse(
            data = testUser
        )
        coEvery {
            encryptedUserDatastore.saveUser(testUser.toUserModel())
        } returns Unit
        userRepositoryImpl.fetchUser()
    }


    @Test
    fun `fetch user fail`() = runTest {
        val error = Exception()
        coEvery {
            authService.getUser()
        } throws error

        try {
            userRepositoryImpl.fetchUser()
        } catch (e: Exception) {
            e shouldBe error
        }
    }
}