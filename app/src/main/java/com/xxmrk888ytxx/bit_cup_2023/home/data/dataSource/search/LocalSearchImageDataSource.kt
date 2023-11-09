package com.xxmrk888ytxx.bit_cup_2023.home.data.dataSource.search

import com.xxmrk888ytxx.bit_cup_2023.home.data.api.model.ImageDto
import java.util.WeakHashMap
import javax.inject.Inject

class LocalSearchImageDataSource @Inject constructor() {

    private val weakHashMap = WeakHashMap<String, List<ImageDto>>()

    fun addImages(query: String, images: List<ImageDto>) {
        weakHashMap[query] = images
    }

    fun getImagesBySearchQuery(query: String): List<ImageDto>? = weakHashMap[query]
}