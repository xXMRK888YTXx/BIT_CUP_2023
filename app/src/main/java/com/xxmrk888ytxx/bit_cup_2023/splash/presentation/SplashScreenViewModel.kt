package com.xxmrk888ytxx.bit_cup_2023.splash.presentation

import androidx.lifecycle.viewModelScope
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
import com.xxmrk888ytxx.bit_cup_2023.domain.useCase.GetCategoriesUseCase
import com.xxmrk888ytxx.bit_cup_2023.domain.useCase.GetCuratedImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getCuratedImageUseCase: GetCuratedImageUseCase,
) : BaseViewModel() {

    private val _isAnimationInProgress = MutableStateFlow(false)
    val isAnimationInProgress = _isAnimationInProgress.asStateFlow()

    private val isCategoryLoaded = MutableStateFlow(false)
    private val isCuratedImagesLoaded = MutableStateFlow(false)
    private val isAnimationFinished = MutableStateFlow(false)

    private val isPrepareFinished =
        combine(isAnimationFinished, isCategoryLoaded, isCuratedImagesLoaded) { prepareStateArray ->
            prepareStateArray.all { it }
        }

    fun preloadData() {
        if (_isAnimationInProgress.value) return
        _isAnimationInProgress.update { true }

        viewModelScope.launch(Dispatchers.IO) {
            getCategoriesUseCase()
            isCategoryLoaded.update { true }
        }

        viewModelScope.launch(Dispatchers.IO) {
            getCuratedImageUseCase()
            isCuratedImagesLoaded.update { true }
        }
    }

    fun onAnimationFinished() {
        isAnimationFinished.update { true }
    }

    init {
        viewModelScope.launch {
            isPrepareFinished.collect {
                if (it) {
                    sendNavigationAction(NavigationAction.Navigate(Screen.Home, Screen.Splash))
                }
            }
        }
    }

}