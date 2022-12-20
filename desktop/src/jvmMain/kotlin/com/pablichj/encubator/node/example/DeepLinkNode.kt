package com.pablichj.encubator.node.example

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.navigation.Path
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DeepLinkNode(
    parentContext: NodeContext,
    val onDeepLinkClick: (path: Path) -> Unit,
    val onCloseClick: () -> Unit
) : Node(parentContext) {
    private val windowState = WindowState()

    private val deepLinks = mutableListOf<Path>(
        getDeepLinkPath1(),
        getDeepLinkPath2(),
        getDeepLinkPath3()
    )

    private fun getDeepLinkPath1(): Path {
        return Path("App")
            .appendSubPath("AdaptableWindow")
            .appendSubPath("Drawer")
            .appendSubPath("Orders")
            .appendSubPath("Past")
            .appendSubPath("Page3")
    }

    private fun getDeepLinkPath2(): Path {
        return Path("App")
            .appendSubPath("AdaptableWindow")
            .appendSubPath("Drawer")
            .appendSubPath("Home")
            .appendSubPath("Page2")
    }

    private fun getDeepLinkPath3(): Path {
        return Path("App")
            .appendSubPath("AdaptableWindow")
            .appendSubPath("Drawer")
            .appendSubPath("Settings")
            .appendSubPath("Page3")
    }

    @Composable
    override fun Content(modifier: Modifier) {

        Window(
            state = windowState,
            onCloseRequest = {
                onCloseClick()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxHeight().wrapContentWidth()
            ) {
                items(
                    items = deepLinks,
                    itemContent = { path ->
                        Text(
                            modifier = Modifier.clickable(
                                enabled = true
                            ) {
                                onDeepLinkClick(path.moveToStart())
                            },
                            text = path.toString(),
                            style = TextStyle(fontSize = 18.sp)
                        )
                    })
            }
        }

        LaunchedEffect(windowState) {
            launch {
                snapshotFlow { windowState.isMinimized }
                    .onEach {
                        //onWindowMinimized(activeNode, it)
                    }
                    .launchIn(this)
            }
        }

    }

}