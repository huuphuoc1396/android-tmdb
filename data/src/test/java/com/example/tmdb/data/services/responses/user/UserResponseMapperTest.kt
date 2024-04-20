package com.example.tmdb.data.services.responses.user

import com.example.tmdb.domain.models.user.UserModel
import io.kotest.matchers.shouldBe
import org.junit.Test

class UserResponseMapperTest {
    @Test
    fun `map UserResponse to UserModel`() {
        val userResponse = UserResponse(
            id = "1", name = "name 1", email = "email1@yahoo.com", avatarUrl = "http://www.google.com"
        )
        userResponse.toUserModel() shouldBe UserModel(
            id = "1", name = "name 1", email = "email1@yahoo.com", avatarUrl = "http://www.google.com"
        )
    }
}