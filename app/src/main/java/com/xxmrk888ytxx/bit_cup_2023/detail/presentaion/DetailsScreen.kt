package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Alignment
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
    val isImageBookmarked by detailsViewModel.bookmarkState.collectAsState(initial = false)

    CollectNavigationAction(
        navController = navController,
        navigationAction = detailsViewModel.navigationAction
    )
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = theme.background,
        topBar = { Topbar(screenState) { detailsViewModel.navigateUp() } },
        bottomBar = {
            if (screenState is DetailsScreenState.Loaded) {
                BottomBar(
                    isImageBookmarked = isImageBookmarked,
                    onDownload = { detailsViewModel.downloadImage() },
                    onBookmarkStateChange = {
                        detailsViewModel.onChangeBookmarkState(isImageBookmarked)
                    }
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (screenState) {
                DetailsScreenState.ImageNotFound -> ImageNotFoundStub {
                    detailsViewModel.navigateUp()
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

@Composable
private fun BottomBar(
    isImageBookmarked: Boolean,
    onDownload: () -> Unit,
    onBookmarkStateChange: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 24.dp,
                bottom = 24.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(100))
                .background(theme.bottomBarButtonsBackground)
                .clickable(onClick = onDownload),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onDownload,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(100))
                    .background(theme.downloadIconBackground),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.download_icon),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = theme.downloadIcon
                )
            }

            Text(
                text = stringResource(R.string.download),
                modifier = Modifier.padding(
                    start = 16.dp,
                    bottom = 14.dp,
                    top = 15.dp,
                    end = 36.dp
                ),
                style = TextStyle(
                    color = theme.downloadWidgetText,
                    fontFamily = ApplicationFont.mulish,
                    fontWeight = FontWeight.W600,
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        IconButton(
            onClick = onBookmarkStateChange,
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(100))
                .background(theme.bottomBarButtonsBackground)
        ) {
            Icon(
                painter = if (isImageBookmarked) painterResource(id = R.drawable.bookmark_selected)
                else painterResource(id = R.drawable.bookmark_no_selected),
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp),
                tint = when (isImageBookmarked) {
                    true -> theme.bookmarkSelectedColor
                    false -> theme.bookmarkNoSelectedColor
                }
            )

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
