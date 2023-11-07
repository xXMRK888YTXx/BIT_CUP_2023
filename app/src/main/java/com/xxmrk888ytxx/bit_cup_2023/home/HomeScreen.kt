package com.xxmrk888ytxx.bit_cup_2023.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
            .padding(24.dp)
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
            if(text.isNotEmpty()) {
                IconButton(onClick = { onTextChanged("")}) {
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
