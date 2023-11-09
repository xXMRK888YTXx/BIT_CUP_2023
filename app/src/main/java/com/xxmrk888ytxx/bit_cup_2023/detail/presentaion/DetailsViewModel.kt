package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase.GetImageUseCase
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.model.DetailsScreenState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailsViewModel @AssistedInject constructor(
    @Assisted private val imageId: Long,
    @Assisted private val imageSourceType: ImageSourceType,
    private val getImageUseCase: GetImageUseCase,
) : BaseViewModel() {

    private val _screenState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    fun navigateUp() {
        sendNavigationAction(NavigationAction.NavigateUp)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getImageUseCase(imageSourceType, imageId)
                .onSuccess { image -> _screenState.update { DetailsScreenState.Loaded(image) } }
                .onFailure { _screenState.update { DetailsScreenState.ImageNotFound } }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            imageId: Long,
            imageSourceType: ImageSourceType,
        ): DetailsViewModel
    }
}