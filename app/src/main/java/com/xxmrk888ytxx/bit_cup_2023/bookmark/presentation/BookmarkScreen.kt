package com.xxmrk888ytxx.bit_cup_2023.bookmark.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseStub
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.ImageList
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.LoadingIndicator
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.SetupNavigationAndStatusBarColor
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.CollectNavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.ApplicationFont
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    bookmarkViewModel: BookmarkViewModel = hiltViewModel(),
    navController: NavController,
) {
    CollectNavigationAction(
        navController = navController,
        navigationAction = bookmarkViewModel.navigationAction
    )

    SetupNavigationAndStatusBarColor(color = theme.background)

    val isLoading by bookmarkViewModel.isLoading.collectAsState()
    val images by bookmarkViewModel.bookmarkImages.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.bookmark),
                        style = TextStyle(
                            fontFamily = ApplicationFont.mulish,
                            color = theme.topbarText,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.W700
                        )
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = theme.background
                )
            )
        },
        containerColor = theme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (isLoading) {
                LoadingIndicator()
            }

            if (images.isNotEmpty()) {
                ImageList(
                    images = images,
                    onDetailsScreen = { bookmarkViewModel.onDetailsScreen(it) },
                    onPlaceholderText = { "${it.name}\n${it.author}" },
                    placeholderMaxLines = 2
                )
            } else {
                BookmarkImagesEmpty { bookmarkViewModel.toHomeScreen() }
            }
        }
    }
}

@Composable
fun BookmarkImagesEmpty(
    onExplore: () -> Unit,
) {
    BaseStub(
        textStub = stringResource(R.string.you_haven_t_saved_anything_yet),
        onExplore = onExplore
    )
}
