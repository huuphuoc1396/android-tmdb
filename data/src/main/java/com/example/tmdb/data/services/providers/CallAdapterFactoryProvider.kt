package com.example.tmdb.data.services.providers

import com.example.tmdb.data.services.adapters.MappingApiErrorCallAdapterFactory

object CallAdapterFactoryProvider {

    fun getMappingApiErrorCallAdapterFactory(): MappingApiErrorCallAdapterFactory {
        return MappingApiErrorCallAdapterFactory.create()
    }
}
