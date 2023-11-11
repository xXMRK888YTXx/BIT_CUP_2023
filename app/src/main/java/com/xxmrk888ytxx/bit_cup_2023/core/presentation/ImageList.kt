package com.xxmrk888ytxx.bit_cup_2023.core.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.ApplicationFont
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageList(
    images: List<Image>,
    onDetailsScreen: (Long) -> Unit,
    placeholderMaxLines: Int,
    onPlaceholderText: (Image) -> String,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 12.dp
            )
    ) {
        items(images, key = { it.id }) { image ->
            var isLoading by remember {
                mutableStateOf(true)
            }

            Box(modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)) {
                SubcomposeAsyncImage(
                    onLoading = {
                        isLoading = true
                    },
                    onSuccess = {
                        isLoading = false
                    },
                    model = image.imageUrl,
                    contentDescription = "",
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .animateItemPlacement()
                        .clickable {
                            onDetailsScreen(image.id)
                        }
                        .shimmerEffect(),
                    success = {
                        val placeholderText = remember(onPlaceholderText, image) {
                            onPlaceholderText(image)
                        }

                        SubcomposeAsyncImageContent()
                        if (placeholderText.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.BottomCenter)
                                    .fillMaxWidth()
                                    .background(theme.imagePlaceholder),
                                Alignment.Center
                            ) {
                                Text(
                                    text = placeholderText,
                                    modifier = Modifier.padding(8.dp),
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontFamily = ApplicationFont.mulish,
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        color = theme.imagePlaceholderText
                                    ),
                                    maxLines = placeholderMaxLines,
                                )
                            }
                        }
                    }
                )

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .shimmerEffect()
                            .fillMaxSize()
                            .padding(12.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .animateItemPlacement()
                    )
                }
            }
        }
    }
}