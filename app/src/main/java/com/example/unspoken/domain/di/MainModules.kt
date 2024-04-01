package com.example.unspoken.domain.di

import com.example.unspoken.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModules {

    @Singleton
    @Provides
    fun getMainRepository() = MainRepository()
}
