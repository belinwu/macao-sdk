package com.pablichj.incubator.uistate3.example.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.incubator.uistate3.node.NavigatorNodeItem
import com.pablichj.incubator.uistate3.node.NodeContext
import com.pablichj.incubator.uistate3.node.adaptable.AdaptableSizeNode
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.navbar.NavBarNode
import com.pablichj.incubator.uistate3.node.navigation.SubPath
import example.nodes.OnboardingNode

object AdaptableSizeTreeBuilder {

    private val rootParentNodeContext = NodeContext.Root()
    private lateinit var AdaptableSizeNode: AdaptableSizeNode
    private lateinit var subTreeNavItems: MutableList<NavigatorNodeItem>

    fun getOrCreateAdaptableSizeNode(
        windowSizeInfoProvider: IWindowSizeInfoProvider
    ): AdaptableSizeNode {

        if (AdaptableSizeTreeBuilder::AdaptableSizeNode.isInitialized) {
            return AdaptableSizeNode.apply {
                this.context.parentContext = rootParentNodeContext
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableSizeNode(
            rootParentNodeContext,
            windowSizeInfoProvider
        ).also {
            it.context.subPath = SubPath("AdaptableWindow")
            AdaptableSizeNode = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NavigatorNodeItem> {

        if (AdaptableSizeTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        // Temporary empty context until the navItems get attached to a NavigatorNode.
        // At that point the navItems parent context will point to the NavigatorNode's context
        val TemporalEmptyContext = NodeContext(null)

        val NavBarNode = NavBarNode(TemporalEmptyContext)
            .apply { context.subPath = SubPath("Orders") }

        val navbarNavItems = mutableListOf(
            NavigatorNodeItem(
                label = "Current",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    NavBarNode.context, "Orders / Current", Icons.Filled.Home, {}
                ).apply { context.subPath = SubPath("Current") },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Past",
                icon = Icons.Filled.Edit,
                node = OnboardingNode(
                    NavBarNode.context, "Orders / Past", Icons.Filled.Edit, {}
                ).apply { context.subPath = SubPath("Past") },
                selected = false
            ),
            NavigatorNodeItem(
                label = "Claim",
                icon = Icons.Filled.Email,
                node = OnboardingNode(NavBarNode.context, "Orders / Claim", Icons.Filled.Email, {})
                    .apply { context.subPath = SubPath("Claim") },
                selected = false
            )
        )

        NavBarNode.setNavItems(navbarNavItems, 0)

        val SettingsNode =
            OnboardingNode(TemporalEmptyContext, "Settings", Icons.Filled.Email, {})
                .apply { context.subPath = SubPath("Settings") }

        val navItems = mutableListOf(
            NavigatorNodeItem(
                label = "Home",
                icon = Icons.Filled.Home,
                node = OnboardingNode(
                    TemporalEmptyContext, "Home", Icons.Filled.Home, {}
                ).apply { context.subPath = SubPath("Home") },
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
                node = SettingsNode,
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}