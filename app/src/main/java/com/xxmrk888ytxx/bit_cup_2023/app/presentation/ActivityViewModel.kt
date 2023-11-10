package com.xxmrk888ytxx.bit_cup_2023.app.presentation

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BottomBarType
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor() : ViewModel() {

    private val _isNeedShowBottomBar = MutableStateFlow(false)
    val isNeedShowBottomBar = _isNeedShowBottomBar.asStateFlow()

    private val _selectedBottomScreen = MutableStateFlow(BottomBarType.HOME)
    val selectedBottomBarScreen = _selectedBottomScreen.asStateFlow()

    private var isHavePendingInstallDestinationListenerRequest = false

    var navController: NavHostController? = null
        set(value) {
            field = value
            if (isHavePendingInstallDestinationListenerRequest) {
                isHavePendingInstallDestinationListenerRequest = false
                setupDestinationListener()
            }
        }

    private val destinationListener =
        NavController.OnDestinationChangedListener { _, destination, _ ->
            when (destination.route) {
                Screen.Home.route -> {
                    _isNeedShowBottomBar.update { true }
                }

                else -> _isNeedShowBottomBar.update { false }
            }
        }

    fun changeBottomBarScreen(bottomBarType: BottomBarType) {
        _selectedBottomScreen.update { bottomBarType }
    }

    fun setupDestinationListener() {
        navController?.addOnDestinationChangedListener(destinationListener)
            ?: run { isHavePendingInstallDestinationListenerRequest = true }
    }

    fun removeDestinationListener() {
        navController?.removeOnDestinationChangedListener(destinationListener)
    }
}