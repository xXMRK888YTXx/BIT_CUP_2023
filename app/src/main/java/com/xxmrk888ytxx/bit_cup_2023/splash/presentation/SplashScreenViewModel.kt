package com.xxmrk888ytxx.bit_cup_2023.splash.presentation

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class SplashScreenViewModel @Inject constructor() : BaseViewModel() {

    private val _isAnimationInProgress = MutableStateFlow(false)
    val isAnimationInProgress = _isAnimationInProgress.asStateFlow()

    fun preloadData() {
        if(_isAnimationInProgress.value) return
        _isAnimationInProgress.update { true }
    }

    fun onAnimationFinished() {
        sendNavigationAction(NavigationAction.Navigate(Screen.Home,Screen.Splash))
    }

}