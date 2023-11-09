package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseStub
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.LoadingIndicator
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.CollectNavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.ApplicationFont
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.model.DetailsScreenState

@Composable
fun DetailsScreen(detailsViewModel: DetailsViewModel, navController: NavController) {

    val screenState by detailsViewModel.screenState.collectAsState()
    CollectNavigationAction(
        navController = navController,
        navigationAction = detailsViewModel.navigationAction
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = theme.background,
        topBar = { Topbar(screenState) { detailsViewModel.navigateUp() } }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (screenState) {
                DetailsScreenState.ImageNotFound -> ImageNotFoundStub {

                }

                is DetailsScreenState.Loaded -> {
                    AsyncImage(
                        model = (screenState as DetailsScreenState.Loaded).image.imageUrl,
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                DetailsScreenState.Loading -> {
                    LoadingIndicator()
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Topbar(
    screenState: DetailsScreenState,
    onNavigateUp: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            if (screenState is DetailsScreenState.Loaded) {
                Text(
                    text = screenState.image.author,
                    maxLines = 1,
                    style = TextStyle(
                        color = theme.detailsScreenTopbarText,
                        fontFamily = ApplicationFont.mulish,
                        fontWeight = FontWeight.W700,
                        fontSize = 18.sp
                    )
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateUp,
                modifier = Modifier
                    .padding(start = 24.dp)
                    .size(40.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(theme.detailsScreenTopbarNavigationButtonBackground)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back_arrow),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = theme.detailsScreenTopbarNavigationButton
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = theme.background
        ),
        modifier = Modifier.padding(
            top = 16.dp
        )
    )
}

@Composable
fun ImageNotFoundStub(
    onExplore: () -> Unit,
) {
    BaseStub(textStub = stringResource(R.string.image_not_found), onExplore = onExplore)
}
