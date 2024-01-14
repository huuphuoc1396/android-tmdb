package com.example.tmdb.data.repositories

import com.example.tmdb.data.services.AuthService
import com.example.tmdb.data.services.responses.user.toUserModel
import com.example.tmdb.data.storages.datastores.EncryptedUserDatastore
import com.example.tmdb.domain.models.user.UserModel
import com.example.tmdb.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val authApiService: AuthService,
    private val encryptedUserDatastore: EncryptedUserDatastore,
) : UserRepository {

    override suspend fun fetchUser() {
        val userResponse = authApiService.getUser().data
        val userModel = userResponse?.toUserModel()
        if (userModel != null) {
            encryptedUserDatastore.saveUser(userModel)
        }
    }

    override fun getUser(): Flow<UserModel> {
        return encryptedUserDatastore.getUser()
    }
}