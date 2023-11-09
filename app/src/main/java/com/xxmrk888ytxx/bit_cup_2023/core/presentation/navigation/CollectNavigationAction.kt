package com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen.Companion.ARGUMENT_BUNDLE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun CollectNavigationAction(
    navController: NavController,
    navigationAction: Flow<NavigationAction>,
) {
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.collect { action ->
            withContext(Dispatchers.Main) {
                when (action) {
                    is NavigationAction.Navigate -> {
                        navController.navigate(action.screen.route) {
                            launchSingleTop = true
                            action.popTo?.let {
                                popUpTo(it.route) {
                                    inclusive = true
                                }
                            }
                        }

                        action.args?.let {
                            navController
                                .getBackStackEntry(action.screen.route)
                                .savedStateHandle[ARGUMENT_BUNDLE_KEY] = it
                        }
                    }

                    NavigationAction.NavigateUp -> navController.navigateUp()
                }
            }
        }
    })
}