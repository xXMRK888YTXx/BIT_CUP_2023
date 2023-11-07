package com.xxmrk888ytxx.bit_cup_2023.home.data.api

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.CategoriesResponse
import retrofit2.http.GET

interface CategoriesApi {
    @GET("v1/collections/featured?per_page=7")
    suspend fun getCategories(): CategoriesResponse
}