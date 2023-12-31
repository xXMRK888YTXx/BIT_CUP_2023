package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase.ChangeBookmarkStateUseCase
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase.DownloadImageUseCase
import com.xxmrk888ytxx.bit_cup_2023.detail.domain.useCase.GetBookmarkStateUseCase
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
    private val changeBookmarkStateUseCase: ChangeBookmarkStateUseCase,
    getBookmarkStateUseCase: GetBookmarkStateUseCase,
    private val downloadImageUseCase: DownloadImageUseCase,
) : BaseViewModel() {

    private val _screenState = MutableStateFlow<DetailsScreenState>(DetailsScreenState.Loading)
    val screenState = _screenState.asStateFlow()

    val bookmarkState = getBookmarkStateUseCase(imageId)

    fun navigateUp() {
        sendNavigationAction(NavigationAction.NavigateUp)
    }

    fun onChangeBookmarkState(isImageBookmarked: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            changeBookmarkStateUseCase(imageId, !isImageBookmarked)
        }
    }

    fun downloadImage() {
        viewModelScope.launch(Dispatchers.IO) {
            val image = (_screenState.value as? DetailsScreenState.Loaded)?.image ?: return@launch
            downloadImageUseCase(image)
        }
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