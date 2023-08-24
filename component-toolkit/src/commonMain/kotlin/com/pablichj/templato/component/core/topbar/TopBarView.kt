package com.pablichj.templato.component.core.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    presenter: TopBarStatePresenter
) {
    val state = presenter.topBarState.value
    val topBarStyle = presenter.topBarStyle
    val topBarIcon1: ImageVector? = state.icon1
    val topBarIcon2: ImageVector? = state.icon2
    val topBarTitle: String? = state.title

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(topBarStyle.barHeight)
                .background(topBarStyle.barColor)
                .border(1.dp, topBarStyle.borderColor)
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            topBarIcon1?.let {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            state.onIcon1Click?.invoke()
                        },
                    imageVector = it,
                    contentDescription = "com.pablichj.templato.component.core.TopBar icon"
                )
            }

            topBarIcon2?.let {
                Icon(
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .clickable {
                            state.onIcon2Click?.invoke()
                        },
                    imageVector = it,
                    contentDescription = "com.pablichj.templato.component.core.TopBar icon"
                )
            }

            topBarTitle?.let {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 8.dp)
                        .clickable {
                            state.onTitleClick?.invoke()
                        },
                    text = it,
                    fontSize = topBarStyle.textSize
                )
            }

        }
    }

}
