package com.pablichj.encubator.node.example

import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.pablichj.encubator.node.Node
import com.pablichj.encubator.node.NodeContext

class DesktopAppNode(
    parentContext: NodeContext
) : Node(parentContext) {
    private val activeWindows = mutableStateListOf<WindowNode>()

    private val MainWindowNode = MainWindowNode(
        parentContext = context,
        onOpenDeepLinkClick = {
            openDeepLinkWindow()
        },
        onRootNodeSelection = {
            openWindow(it)
        },
        onExitClick = { exit() }
    )

    private val DeepLinkNode = DeepLinkNode(
        context,
        onDeepLinkClick = { path ->
            MainWindowNode.handleDeepLink(path)
        },
        onCloseClick = {
            closeDeepLinkWindow()
        }
    )

    private val DrawerWindowNode = DrawerWindowNode(
        context,
        onCloseClick = {
            closeDrawerWindowNode()
        }
    )

    private val NavBarWindowNode = NavBarWindowNode(
        context,
        onCloseClick = {
            closeNavBarWindowNode()
        }
    )

    private val PanelWindowNode = PanelWindowNode(
        context,
        onCloseClick = {
            closePanelWindowNode()
        }
    )

    private val FullAppWindowNode = FullAppWindowNode(
        context,
        onCloseClick = {
            closeFullAppWindowNode()
        }
    )

    init {
        activeWindows.add(MainWindowNode)
    }

    private fun openDeepLinkWindow() {
        if (!activeWindows.contains(DeepLinkNode)) {
            activeWindows.add(DeepLinkNode)
        }
    }

    private fun openWindow(windowNodeSample: WindowNodeSample) {
        val window = when (windowNodeSample) {
            WindowNodeSample.Drawer -> DrawerWindowNode
            WindowNodeSample.Navbar -> NavBarWindowNode
            WindowNodeSample.Panel -> PanelWindowNode
            WindowNodeSample.FullApp -> FullAppWindowNode
        }
        if (!activeWindows.contains(window)) {
            activeWindows.add(window)
        }
    }

    private fun closeDeepLinkWindow() {
        activeWindows.remove(DeepLinkNode)
    }

    private fun closeDrawerWindowNode() {
        activeWindows.remove(DrawerWindowNode)
    }

    private fun closeNavBarWindowNode() {
        activeWindows.remove(NavBarWindowNode)
    }

    private fun closePanelWindowNode() {
        activeWindows.remove(PanelWindowNode)
    }

    private fun closeFullAppWindowNode() {
        activeWindows.remove(FullAppWindowNode)
    }

    private fun exit() {
        activeWindows.clear()
    }

    @Composable
    override fun Content(modifier: Modifier) {
        for (window in activeWindows) {
            key(window) {
                window.Content(modifier)
            }
        }
    }

}