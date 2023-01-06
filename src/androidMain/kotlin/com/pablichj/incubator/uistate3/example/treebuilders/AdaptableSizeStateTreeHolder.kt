package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.lifecycle.ViewModel
import com.pablichj.incubator.uistate3.node.BackPressedCallback
import com.pablichj.incubator.uistate3.node.IBackPressDispatcher
import com.pablichj.incubator.uistate3.node.NodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.adaptable.AdaptableSizeNode
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.drawer.DrawerNode
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import example.nodes.OnboardingNode
import com.pablichj.incubator.uistate3.node.panel.PanelNode

class AdaptableSizeStateTreeHolder : ViewModel() {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var AdaptableSizeNode: AdaptableSizeNode
    private lateinit var subTreeNavItems: MutableList<NodeItem>

    fun getOrCreate(
        windowSizeInfoProvider: IWindowSizeInfoProvider,
        backPressDispatcher: IBackPressDispatcher,
        backPressedCallback: BackPressedCallback
    ): AdaptableSizeNode {

        // Update the back pressed dispatcher with the new Activity OnBackPressDispatcher.
        rootParentNodeContext.backPressDispatcher = backPressDispatcher
        rootParentNodeContext.backPressedCallbackDelegate = backPressedCallback

        if (this::AdaptableSizeNode.isInitialized) {
            return AdaptableSizeNode.apply {
                this.context.parentContext = rootParentNodeContext
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableSizeNode(
            rootParentNodeContext,
            windowSizeInfoProvider
        ).also {
            it.setNavItems(
                getOrCreateDetachedNavItems(), 0
            )
            it.setCompactContainer(DrawerNode(it.context))
            it.setMediumContainer(NavBarNode(it.context))
            it.setExpandedContainer(PanelNode(it.context))
            AdaptableSizeNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NodeItem> {

        if (this::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val TemporalEmptyContext = NodeContext.Root()

        val NavBarNode = NavBarNode(TemporalEmptyContext)

        val navbarNavItems = mutableListOf(
            NodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(NavBarNode.context, "Orders / Current", Icons.Filled.Home) {},
                selected = false
            ),
            NodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(NavBarNode.context, "Orders / Past", Icons.Filled.Edit) {},
                selected = false
            ),
            NodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email) {},
                selected = false
            )
        )

        NavBarNode.setItems(navbarNavItems, 0)

        val navItems = mutableListOf(
            NodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    TemporalEmptyContext,
                    "Home",
                    Icons.Filled.Home
                ) {},
                selected = false
            ),
            NodeItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                node = NavBarNode,
                selected = false
            ),
            NodeItem(
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