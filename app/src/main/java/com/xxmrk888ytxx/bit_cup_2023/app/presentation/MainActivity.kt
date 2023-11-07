package com.xxmrk888ytxx.bit_cup_2023.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.Screen
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.setContentWithTheme
import com.xxmrk888ytxx.bit_cup_2023.splash.presentation.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentWithTheme {
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = Screen.Splash.route) {
                composable(Screen.Splash.route) {
                    SplashScreen(navController = navController)
                }

                composable(Screen.Home.route) {

                }
            }
        }
    }
}
