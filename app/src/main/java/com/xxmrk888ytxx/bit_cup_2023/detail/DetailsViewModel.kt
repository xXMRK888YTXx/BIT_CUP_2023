package com.xxmrk888ytxx.bit_cup_2023.detail

import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val imageId: Long,
    @Assisted private val imageSourceType: ImageSourceType,
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(
            imageId: Long,
            imageSourceType: ImageSourceType,
        ): DetailsViewModel
    }
}