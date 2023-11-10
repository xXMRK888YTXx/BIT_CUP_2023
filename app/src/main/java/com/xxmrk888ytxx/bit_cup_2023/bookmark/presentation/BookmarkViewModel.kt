package com.xxmrk888ytxx.bit_cup_2023.bookmark.presentation

import androidx.core.os.bundleOf
import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.bookmark.domain.useCase.GetBookmarkStateChangesUseCase
import com.xxmrk888ytxx.bit_cup_2023.bookmark.domain.useCase.GetBookmarkedImageUseCase
import com.xxmrk888ytxx.bit_cup_2023.bookmark.domain.useCase.GetBookmarkedImages
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.ImageSourceType
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val getBookmarkedImages: GetBookmarkedImages,
    private val getBookmarkStateChangesUseCase: GetBookmarkStateChangesUseCase,
    private val getBookmarkedImageUseCase: GetBookmarkedImageUseCase,
) : BaseViewModel() {

    private val _bookmarkImages = MutableStateFlow(emptyList<Image>())
    val bookmarkImages = _bookmarkImages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    private fun loadImages() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.update { true }
            getBookmarkedImages()
                .onSuccess { bookmarkedImages -> _bookmarkImages.update { bookmarkedImages } }
            _isLoading.update { false }
        }
    }

    fun toHomeScreen() {
        sendNavigationAction(
            NavigationAction.Navigate(
                Screen.Home,
                Screen.Home
            )
        )
    }

    fun onDetailsScreen(imageId: Long) {
        sendNavigationAction(
            NavigationAction.Navigate(
                screen = Screen.Details,
                args = bundleOf(
                    Screen.Details.IMAGE_ID_NAVIGATION_ARGUMENT_KEY to imageId,
                    Screen.Details.IMAGE_SOURCE_ARGUMENT_KEY to ImageSourceType.CACHE.id,
                )
            )
        )
    }


    private fun collectBookmarkChanges() {
        viewModelScope.launch(Dispatchers.IO) {
            getBookmarkStateChangesUseCase().collect { action ->
                if (action.isAddedToBookmark) {
                    getBookmarkedImageUseCase(action.imageId)
                        .onSuccess { image ->
                            _bookmarkImages.update { images -> images + image }
                        }
                } else {
                    _bookmarkImages.update {
                        val mutableList = it.toMutableList()
                        mutableList.filter { image -> image.id != action.imageId }
                    }
                }
            }
        }
    }

    init {
        loadImages()
        collectBookmarkChanges()
    }
}