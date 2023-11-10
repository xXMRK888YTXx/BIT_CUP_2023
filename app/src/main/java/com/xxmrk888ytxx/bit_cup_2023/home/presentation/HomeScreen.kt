package com.xxmrk888ytxx.bit_cup_2023.home.presentation

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.W400
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.BaseStub
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.LoadingIndicator
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.CollectNavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.ApplicationFont
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.lightColors
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme
import com.xxmrk888ytxx.bit_cup_2023.domain.model.Image
import com.xxmrk888ytxx.bit_cup_2023.home.domain.model.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
) {
    val screenState by homeViewModel.screenState.collectAsState()

    CollectNavigationAction(
        navController = navController,
        navigationAction = homeViewModel.navigationAction
    )

    val context = LocalContext.current

    LaunchedEffect(key1 = homeViewModel.toastAction, block = {
        withContext(Dispatchers.Main) {
            homeViewModel.toastAction.collect {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    })
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                text = screenState.searchBarScreen,
                onTextChanged = homeViewModel::onSearchTextChanged
            )
        },
        containerColor = theme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            CategoryList(
                categories = screenState.categories,
                searchBarText = screenState.searchBarScreen,
                onCategoryClicked = {
                    homeViewModel.onSearchTextChanged(it.categoryName)
                }
            )
            if (screenState.isLoading) {
                LoadingIndicator()
            }

            when {
                screenState.isInternetError -> InternetErrorStub {
                    homeViewModel.onRetryLoadImage()
                }

                screenState.images.isNotEmpty() -> ImageList(
                    images = screenState.images,
                    onDetailsScreen = { imageId ->
                        homeViewModel.onOpenNavigationAction(imageId)
                    }
                )

                !screenState.isLoading -> ImageNotFoundStub {
                    homeViewModel.onSearchTextChanged("")
                    homeViewModel.loadCuratedImages()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageList(
    images: List<Image>,
    onDetailsScreen: (Long) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp
            )
    ) {
        itemsIndexed(images, key = { _, it -> it.id }) { index, image ->
            SubcomposeAsyncImage(
                model = image.imageUrl,
                contentDescription = "",
                modifier = Modifier
                    .padding(
                        start = if (index % 2 == 0) 0.dp else 12.dp,
                        end = if (index % 2 != 0) 0.dp else 12.dp,
                        bottom = 12.dp,
                        top = 12.dp
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .animateItemPlacement()
                    .clickable {
                        onDetailsScreen(image.id)
                    },
                loading = {
                    CircularProgressIndicator()
                },
                success = {
                    SubcomposeAsyncImageContent()
                    if (image.name.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .background(theme.imagePlaceholder),
                            Alignment.Center
                        ) {
                            Text(
                                text = image.name,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center,
                                style = TextStyle(
                                    fontFamily = ApplicationFont.mulish,
                                    fontWeight = W400,
                                    fontSize = 14.sp,
                                    color = theme.imagePlaceholderText
                                ),
                                maxLines = 1,
                            )
                        }
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryList(
    categories: List<Category>,
    searchBarText: String,
    onCategoryClicked: (Category) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 24.dp,
                start = 24.dp,
                end = 24.dp
            )
    ) {
        itemsIndexed(categories, key = { _, it -> it.id }) { index, it ->
            val isSelected = remember(categories, searchBarText) {
                it.categoryName == searchBarText
            }

            Button(
                onClick = { onCategoryClicked(it) },
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) theme.selectedCategory else theme.nonSelectedCategory,
                ),
                modifier = Modifier
                    .padding(
                        start = if (index == 0) 0.dp else 11.dp,
                    )
                    .animateItemPlacement(),
            ) {
                Text(
                    text = it.categoryName,
                    style = TextStyle(
                        fontFamily = ApplicationFont.mulish,
                        fontSize = 14.sp,
                        fontWeight = W400,
                        color = if (isSelected) theme.selectedCategoryTextColor
                        else theme.nonSelectedCategoryTextColor
                    )
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    text: String,
    onTextChanged: (String) -> Unit,
) {
    val textStyle = TextStyle(
        fontSize = 14.sp,
        fontFamily = ApplicationFont.mulish,
        fontWeight = W400,
        color = theme.searchBarPlaceholder
    )

    TextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 12.dp,
                start = 24.dp,
                end = 24.dp
            )
            .clip(RoundedCornerShape(50)),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = theme.searchBar,
            unfocusedContainerColor = theme.searchBar,
            focusedLeadingIconColor = lightColors.red,
            unfocusedLeadingIconColor = lightColors.red,
            focusedTrailingIconColor = lightColors.red,
            unfocusedTrailingIconColor = lightColors.red,
            focusedPlaceholderColor = theme.searchBarPlaceholder,
            unfocusedPlaceholderColor = theme.searchBarPlaceholder
        ),
        leadingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.search),
                contentDescription = "",
                modifier = Modifier.size(16.dp),
            )
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                IconButton(onClick = { onTextChanged("") }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_clear_24),
                        contentDescription = "",
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        },
        textStyle = textStyle,
        placeholder = {
            Text(
                text = stringResource(R.string.search),
                style = textStyle
            )
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = { onTextChanged(text) }
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        )
    )
}

@Composable
fun ImageNotFoundStub(
    onExplore: () -> Unit,
) {
    BaseStub(
        textStub = stringResource(R.string.no_results_found),
        onExplore = onExplore
    )
}

@Composable
fun InternetErrorStub(
    onExplore: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.no_network_icon),
            contentDescription = "",
            modifier = Modifier
                .padding(bottom = 24.dp)
                .size(125.dp),
            tint = theme.internetStubColor
        )

        TextButton(onClick = onExplore) {
            Text(
                text = stringResource(R.string.try_again),
                style = TextStyle(
                    color = theme.explore,
                    fontSize = 18.sp,
                    fontWeight = W700,
                    fontFamily = ApplicationFont.mulish
                )
            )
        }
    }
}
