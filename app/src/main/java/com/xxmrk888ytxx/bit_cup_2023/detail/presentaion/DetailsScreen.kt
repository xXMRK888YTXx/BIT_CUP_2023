package com.xxmrk888ytxx.bit_cup_2023.detail.presentaion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme
import com.xxmrk888ytxx.bit_cup_2023.detail.presentaion.model.DetailsScreenState

@Composable
fun DetailsScreen(viewModel: DetailsViewModel) {

    val screenState by viewModel.screenState.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = theme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when (screenState) {
                DetailsScreenState.ImageNotFound -> {}
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

                DetailsScreenState.Loading -> {}
            }

        }
    }
}