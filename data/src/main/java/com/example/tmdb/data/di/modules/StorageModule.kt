package com.example.tmdb.data.di.modules

import com.example.tmdb.data.storages.datastores.EncryptedPrefsDatastore
import com.example.tmdb.data.storages.datastores.EncryptedPrefsDatastoreImpl
import com.example.tmdb.data.storages.datastores.EncryptedUserDatastore
import com.example.tmdb.data.storages.datastores.EncryptedUserDatastoreImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface StorageModule {

    @Binds
    @Singleton
    fun provideEncryptedPrefsDatastore(impl: EncryptedPrefsDatastoreImpl): EncryptedPrefsDatastore

    @Binds
    @Singleton
    fun provideEncryptedUserDatastore(impl: EncryptedUserDatastoreImpl): EncryptedUserDatastore
}