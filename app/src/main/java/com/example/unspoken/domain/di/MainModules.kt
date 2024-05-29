package com.example.unspoken.domain.di

import com.example.unspoken.data.repository.MainRepository
import com.example.unspoken.domain.apis.EmailValidationApi
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object MainModules {
    @Singleton
    @Provides
    fun getMainRepository() = MainRepository()

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideApiService(): EmailValidationApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.apyhub.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(EmailValidationApi::class.java)
    }
}
