package com.xxmrk888ytxx.bit_cup_2023.core.presentation

import androidx.lifecycle.ViewModel
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.NavigationAction
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {
    private val _navigationAction = MutableSharedFlow<NavigationAction>(
        extraBufferCapacity = 1
    )
    val navigationAction = _navigationAction.asSharedFlow()

    protected fun sendNavigationAction(navigationAction: NavigationAction) {
        _navigationAction.tryEmit(navigationAction)
    }
}