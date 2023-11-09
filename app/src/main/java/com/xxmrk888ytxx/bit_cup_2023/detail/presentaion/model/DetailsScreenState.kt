package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.model

import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image

sealed interface DetailsScreenState {
    object Loading : DetailsScreenState

    class Loaded(val image: Image) : DetailsScreenState

    object ImageNotFound : DetailsScreenState
}
