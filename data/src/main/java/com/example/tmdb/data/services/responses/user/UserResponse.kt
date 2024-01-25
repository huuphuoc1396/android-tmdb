package com.example.tmdb.data.services.responses.user

import com.example.tmdb.domain.extensions.defaultEmpty
import com.example.tmdb.domain.models.user.UserModel
import com.squareup.moshi.Json

data class UserResponse(
    @Json(name = "id")
    val id: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "email")
    val email: String?,
    @Json(name = "avatar_url")
    val avatarUrl: String?,
)

fun UserResponse.toUserModel() = UserModel(
    id = id.defaultEmpty(),
    name = name.defaultEmpty(),
    email = email.defaultEmpty(),
    avatarUrl = avatarUrl.defaultEmpty()
)