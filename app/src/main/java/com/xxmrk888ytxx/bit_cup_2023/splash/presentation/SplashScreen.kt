package com.xxmrk888ytxx.bit_cup_2023.splash.presentation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.CollectNavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme

@Composable
fun SplashScreen(
    splashScreenView: SplashScreenViewModel = hiltViewModel(),
    navController: NavController,
) {

    CollectNavigationAction(
        navController = navController,
        navigationAction = splashScreenView.navigationAction
    )

    val isAnimationIsProgress by splashScreenView.isAnimationInProgress.collectAsState()

    val imageAlpha by animateFloatAsState(
        targetValue = if(isAnimationIsProgress) 1f else 0f,
        label = "",
        animationSpec = tween(1000),
        finishedListener = { splashScreenView.onAnimationFinished() }
    )

    LaunchedEffect(key1 = Unit, block = {
        splashScreenView.preloadData()
    })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(theme.splashScreenBackground)
        ,
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = R.drawable.app_logo,
            contentDescription = "",
            modifier = Modifier
                .size(120.dp)
                .alpha(imageAlpha)
        )
    }

}