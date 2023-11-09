package com.xxmrk888ytxx.bit_cup_2023.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AttachPexelsApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val newRequest = originalRequest
            .newBuilder()
            .addHeader(PEXELS_API_KEY_HEADER_NAME, PEXELS_API_KEY)
            .build()

        return chain.proceed(newRequest)
    }

    companion object {
        const val PEXELS_API_KEY_HEADER_NAME = "Authorization"
        const val PEXELS_API_KEY = "3HghbXtiS32o3M9YSgSM6fYGmjRF6u0El16rYhEixphxUfdJXfO9CZPI"
    }
}