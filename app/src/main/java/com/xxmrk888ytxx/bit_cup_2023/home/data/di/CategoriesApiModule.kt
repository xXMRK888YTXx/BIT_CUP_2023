package com.xxmrk888ytxx.bit_cup_2023.home.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.xxmrk888ytxx.bit_cup_2023.home.data.api.AttachPexelsApiKeyInterceptor
import com.xxmrk888ytxx.bit_cup_2023.home.data.api.CategoriesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class CategoriesApiModule {
    @Provides
    fun provideCategoriesApi(): CategoriesApi {
        val okHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(AttachPexelsApiKeyInterceptor())
            .build()

        val json = Json {
            ignoreUnknownKeys = true
        }
        val contentType = CONTENT_TYPE.toMediaType()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        return retrofit.create(CategoriesApi::class.java)
    }

    private companion object {
        const val BASE_URL = "https://api.pexels.com"
        const val CONTENT_TYPE = "application/json"
    }
}