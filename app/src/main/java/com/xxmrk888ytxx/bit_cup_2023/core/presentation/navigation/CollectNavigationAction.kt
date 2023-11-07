package com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

@Composable
fun CollectNavigationAction(
    navController: NavController,
    navigationAction: Flow<NavigationAction>
) {
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.collect {
            withContext(Dispatchers.Main) {
                when(it) {
                    is NavigationAction.Navigate -> navController.navigate(it.screen.route) {
                        launchSingleTop = true
                        it.popTo?.let {
                            popUpTo(it.route) {
                                inclusive = true
                            }
                        }
                    }
                    NavigationAction.NavigateUp -> navController.navigateUp()
                }
            }
        }
    })
}