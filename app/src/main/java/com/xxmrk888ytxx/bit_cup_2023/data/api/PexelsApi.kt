package com.xxmrk888ytxx.bit_cup_2023.data.api

import com.xxmrk888ytxx.bit_cup_2023.data.api.model.CategoriesResponse
import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImageDto
import com.xxmrk888ytxx.bit_cup_2023.data.api.model.ImagesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {
    @GET("v1/collections/featured?per_page=7")
    suspend fun getCategories(): CategoriesResponse

    @GET("/v1/curated?per_page=30")
    suspend fun getCuratedImages(): ImagesResponse

    @GET("/v1/search?per_page=30")
    suspend fun getImagesBySearchQuery(
        @Query("query") query: String,
    ): ImagesResponse

    @GET("/v1/photos/{id}")
    suspend fun getImage(
        @Path("id") imageId: Long,
    ): ImageDto
}