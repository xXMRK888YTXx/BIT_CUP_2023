package com.xxmrk888ytxx.bit_cup_2023.bookmark.presentation

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.bookmark.domain.useCase.GetBookmarkedImages
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
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

    init {
        loadImages()
    }
}