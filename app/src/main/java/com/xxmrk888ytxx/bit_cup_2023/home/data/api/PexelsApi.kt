package com.xxmrk888ytxx.bit_cup_2023.home.data.api

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.CategoriesResponse
import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.CuratedImagesResponse
import retrofit2.http.GET

interface PexelsApi {
    @GET("v1/collections/featured?per_page=7")
    suspend fun getCategories(): CategoriesResponse

    @GET("/v1/curated?per_page=30")
    suspend fun getCuratedImages() : CuratedImagesResponse
}