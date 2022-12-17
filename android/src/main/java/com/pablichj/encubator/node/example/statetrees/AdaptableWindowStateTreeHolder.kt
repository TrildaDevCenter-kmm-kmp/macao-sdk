package com.pablichj.encubator.node.example.statetrees

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.lifecycle.ViewModel
import com.pablichj.encubator.node.BackPressedCallback
import com.pablichj.encubator.node.IBackPressDispatcher
import com.pablichj.encubator.node.NavigatorNodeItem
import com.pablichj.encubator.node.NodeContext
import com.pablichj.encubator.node.adaptable.AdaptableWindowNode
import com.pablichj.encubator.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.encubator.node.drawer.DrawerNode
import com.pablichj.encubator.node.navbar.NavBarNode
import com.pablichj.encubator.node.nodes.OnboardingNode
import com.pablichj.encubator.node.panel.PanelNode

class AdaptableWindowStateTreeHolder : ViewModel() {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var AdaptableWindowNode: AdaptableWindowNode
    private lateinit var subTreeNavItems: MutableList<NavigatorNodeItem>

    fun getOrCreate(
        windowSizeInfoProvider: IWindowSizeInfoProvider,
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): AdaptableWindowNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (this::AdaptableWindowNode.isInitialized) {
            return AdaptableWindowNode.apply {
                this.context.parentContext = rootParentNodeContext
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableWindowNode(
            rootParentNodeContext,
            windowSizeInfoProvider
        ).also {
            it.setNavItems(
                getOrCreateDetachedNavItems(), 0
            )
            it.setCompactNavigator(DrawerNode(it.context))
            it.setMediumNavigator(NavBarNode(it.context))
            it.setExpandedNavigator(PanelNode(it.context))
            AdaptableWindowNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NavigatorNodeItem> {

        if (this::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val TemporalEmptyContext = NodeContext.Root()

        val NavBarNode = NavBarNode(TemporalEmptyContext)

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode.context, "Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        NavBarNode.setNavItems(navbarNavItems, 0)

        val navItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    TemporalEmptyContext,
                    "Home",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NavigatorNodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = NavBarNode,
                selected = false
            ),
            NavigatorNodeItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                node = OnboardingNode(
                    TemporalEmptyContext,
                    "Settings",
                    Icons.Filled.Email
                ) {},
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}