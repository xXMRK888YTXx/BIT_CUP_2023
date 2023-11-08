package com.xxmrk888ytxx.bit_cup_2023.home.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.navigation.CollectNavigationAction
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.ApplicationFont
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.lightColors
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme
import com.xxmrk888ytxx.bit_cup_2023.home.domain.models.Category

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
        }
    }

}

@Composable
fun CategoryList(
    categories: List<Category>,
    searchBarText:String,
    onCategoryClicked:(Category) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        itemsIndexed(categories, key = { _, it -> it.id }) { index, it ->
            val isSelected = remember(categories, searchBarText) {
                it.categoryName == searchBarText
            }

            Button(
                onClick = { onCategoryClicked(it) },
                shape = RoundedCornerShape(100),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) theme.selectedCategory else theme.nonSelectedCategory
                ),
                modifier = Modifier.padding(
                    start = if (index == 0) 0.dp else 11.dp,
                )
            ) {
                Text(
                    text = it.categoryName,
                    style = TextStyle(
                        fontFamily = ApplicationFont.mulish,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
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
        fontWeight = FontWeight.W400,
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
        }
    )

}
