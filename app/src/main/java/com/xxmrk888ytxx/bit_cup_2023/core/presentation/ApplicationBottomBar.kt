package com.xxmrk888ytxx.bit_cup_2023.core.presentation

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xxmrk888ytxx.bit_cup_2023.R
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.lightColors
import com.xxmrk888ytxx.bit_cup_2023.core.presentation.theme.theme


enum class BottomBarType {
    HOME,
    BOOKMARK
}

private data class BottomBarAction(
    @DrawableRes val selectedActionIcon: Int,
    @DrawableRes val noSelectedActionIcon: Int,
    val onClick: () -> Unit,
    val bottomBarType: BottomBarType,
) {
    fun getImage(currentBottomBarType: BottomBarType): Int {
        return if (currentBottomBarType == bottomBarType) selectedActionIcon else noSelectedActionIcon
    }
}

@Composable
fun ApplicationBottomBar(
    onHome: () -> Unit,
    onDetails: () -> Unit,
    currentBottomBarType: BottomBarType,
) {

    val actions = remember {
        listOf(
            BottomBarAction(
                selectedActionIcon = R.drawable.home_button_active,
                noSelectedActionIcon = R.drawable.home_icon_inactive,
                onClick = onHome,
                bottomBarType = BottomBarType.HOME
            ),
            BottomBarAction(
                selectedActionIcon = R.drawable.bookmark_selected,
                noSelectedActionIcon = R.drawable.bookmark_no_selected,
                onClick = onDetails,
                bottomBarType = BottomBarType.BOOKMARK
            )
        )
    }

    val buttonSizes = remember {
        mutableStateMapOf<BottomBarType, Offset>()
    }

    val indicatorXPosition =
        rememberSaveable(currentBottomBarType, buttonSizes[currentBottomBarType]) {
            (buttonSizes[currentBottomBarType] ?: Offset(0f, 0f)).x
        }

    val animatedIndicatorXPosition by animateFloatAsState(
        targetValue = indicatorXPosition,
        label = "",
        animationSpec = tween(500)
    )

    Layout(
        measurePolicy = { measurable, constraints ->
            val indicator = measurable[0].measure(constraints)
            val buttonLayout = measurable[1].measure(constraints)
            layout(buttonLayout.width, buttonLayout.height) {
                buttonLayout.place(0, 0)
                if (indicatorXPosition > 0f && animatedIndicatorXPosition > 0f) {
                    indicator.place(animatedIndicatorXPosition.toInt(), 0)
                }
            }
        },
        content = {
            Spacer(
                modifier = Modifier
                    .width(24.dp)
                    .height(2.dp)
                    .background(lightColors.red)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                actions.forEach {
                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = it.onClick,
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = it.getImage(currentBottomBarType)),
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                                .onPlaced { coordinate ->
                                    buttonSizes[it.bottomBarType] =
                                        coordinate.localToRoot(Offset.Zero)
                                },
                            tint = if (currentBottomBarType == it.bottomBarType) Color.Unspecified
                            else theme.nonSelectedBottomBarAction
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        },
        modifier = Modifier.background(theme.background)
    )
}