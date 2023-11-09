package com.xxmrk888ytxx.bit_cup_2023.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen.Details.IMAGE_SOURCE_ARGUMENT_KEY
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.setContentWithTheme
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.DetailsScreen
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.DetailsViewModel
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.toImageSourceType
import com.xxmrk888ytxx.bit_cup_2023.home.presentation.HomeScreen
import com.xxmrk888ytxx.bit_cup_2023.splash.presentation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var detailsViewModel: DetailsViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithTheme {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.Splash.route) {
                composable(Screen.Splash.route) {
                    SplashScreen(navController = navController)
                }

                composable(Screen.Home.route) {
                    HomeScreen(navController = navController)
                }

                composable(Screen.Details.route) {
                    val argumentBundle = remember(it) {
                        navController
                            .getBackStackEntry(Screen.Details.route)
                            .savedStateHandle
                            .get<Bundle>(Screen.ARGUMENT_BUNDLE_KEY)
                    } ?: return@composable

                    val imageId = remember(argumentBundle) {
                        argumentBundle.getLong(Screen.Details.IMAGE_ID_NAVIGATION_ARGUMENT_KEY, -1)
                    }

                    val imageSource = remember(argumentBundle) {
                        argumentBundle.getInt(IMAGE_SOURCE_ARGUMENT_KEY, -1).toImageSourceType()
                    }

                    val viewModel = viewModel { detailsViewModel.create(imageId, imageSource) }

                    DetailsScreen(viewModel, navController)
                }
            }
        }
    }
}
