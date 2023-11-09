package com.xxmrk888ytxx.bit_cup_2023.core.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.ApplicationFont
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme

@Composable
fun BaseStub(
    textStub: String,
    onExplore: () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = textStub,
            style = TextStyle(
                fontFamily = ApplicationFont.mulish,
                fontWeight = FontWeight.W500,
                color = theme.stubText,
                fontSize = 14.sp
            )
        )

        TextButton(onClick = onExplore) {
            Text(
                text = stringResource(R.string.explore),
                style = TextStyle(
                    color = theme.explore,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    fontFamily = ApplicationFont.mulish
                )
            )
        }
    }
}