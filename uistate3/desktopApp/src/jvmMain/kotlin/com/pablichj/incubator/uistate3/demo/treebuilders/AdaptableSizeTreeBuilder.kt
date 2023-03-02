package com.pablichj.incubator.uistate3.demo.treebuilders

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import com.pablichj.incubator.uistate3.node.NavItem
import com.pablichj.incubator.uistate3.node.adaptable.AdaptableSizeComponent
import com.pablichj.incubator.uistate3.node.adaptable.IWindowSizeInfoProvider
import com.pablichj.incubator.uistate3.node.navbar.NavBarComponent
import com.pablichj.incubator.uistate3.node.setNavItems
import example.nodes.CustomTopBarComponent

object AdaptableSizeTreeBuilder {

    private lateinit var AdaptableSizeComponent: AdaptableSizeComponent
    private lateinit var subTreeNavItems: MutableList<NavItem>

    fun build(
        windowSizeInfoProvider: IWindowSizeInfoProvider
    ): AdaptableSizeComponent {

        if (AdaptableSizeTreeBuilder::AdaptableSizeComponent.isInitialized) {
            return AdaptableSizeComponent.apply {
                this.windowSizeInfoProvider = windowSizeInfoProvider
            }
        }

        return AdaptableSizeComponent(
            windowSizeInfoProvider
        ).also {
            AdaptableSizeComponent = it
        }

    }

    fun getOrCreateDetachedNavItems(): MutableList<NavItem> {

        if (AdaptableSizeTreeBuilder::subTreeNavItems.isInitialized) {
            return subTreeNavItems
        }

        val NavBarComponent = NavBarComponent()

        val navbarNavItems = mutableListOf(NavItem(label = "Current",
            icon = Icons.Filled.Home,
            component = CustomTopBarComponent("Orders / Current", Icons.Filled.Home, {}).apply {
                deepLinkMatcher = { route -> route == "Orders/Page1" }
            },
            selected = false
        ),
            NavItem(label = "Past",
                icon = Icons.Filled.Edit,
                component = CustomTopBarComponent("Orders / Past", Icons.Filled.Edit, {}),
                selected = false),
            NavItem(label = "Claim",
                icon = Icons.Filled.Email,
                component = CustomTopBarComponent("Orders / Claim", Icons.Filled.Email, {}),
                selected = false))

        NavBarComponent.setNavItems(navbarNavItems, 0)

        val SettingsComponent = CustomTopBarComponent("Settings", Icons.Filled.Email, {}).apply {
                deepLinkMatcher = { route -> route == "Settings/Page1" }
            }

        val HomeComponent = CustomTopBarComponent("Home", Icons.Filled.Home, {}).apply {
                deepLinkMatcher = { route -> route == "Home/Page1" }
            }

        val navItems = mutableListOf(
            NavItem(
                label = "Home",
                icon = Icons.Filled.Home,
                component = HomeComponent,
                selected = false
            ), NavItem(
                label = "Orders",
                icon = Icons.Filled.Refresh,
                component = NavBarComponent,
                selected = false
            ), NavItem(
                label = "Settings",
                icon = Icons.Filled.Email,
                component = SettingsComponent,
                selected = false
            )
        )

        return navItems.also { subTreeNavItems = it }
    }

}