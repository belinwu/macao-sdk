package com.pablichj.incubator.uistate3.node.panel

import com.pablichj.incubator.uistate3.node.NodeItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

interface IPanelState {
    /**
     * Intended for the Composable NavBar to render the List if NavBarItems items
     * */
    val navItemsFlow: Flow<List<NodeItem>>

    /**
     * Intended for a client class to listen for navItem click events
     * */
    val navItemClickFlow: Flow<NodeItem>

    /**
     * Intended to be called from the Composable NavBar item click events
     * */
    fun navItemClick(navbarItem: NodeItem)

    /**
     * Intended to be called from a client class to select a navItem in the NavBar
     * */
    fun selectNavItem(navbarItem: NodeItem)
}

class PanelState /*@Inject */ constructor(
    //val dispatchersBin: DispatchersBin
    var navItems: List<NodeItem>
) : IPanelState {

    // TODO: Use DispatchersBin
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private val _navItemsFlow = MutableStateFlow<List<NodeItem>>(emptyList())
    override val navItemsFlow: Flow<List<NodeItem>>
        get() = _navItemsFlow

    private val _navItemClickFlow = MutableSharedFlow<NodeItem>()
    override val navItemClickFlow: Flow<NodeItem>
        get() = _navItemClickFlow

    init {
        _navItemsFlow.value = navItems
    }

    override fun navItemClick(navbarItem: NodeItem) {
        coroutineScope.launch {
            _navItemClickFlow.emit(navbarItem)
        }
    }

    /**
     * To be called by a client class when the Drawer selected item needs to be updated.
     * */
    override fun selectNavItem(navbarItem: NodeItem) {
        coroutineScope.launch {
            updateNavBarSelectedItem(navbarItem)
        }
    }

    private suspend fun updateNavBarSelectedItem(navbarItem: NodeItem) {
        navItems = navItems.map {
            it.copy().apply { selected = navbarItem.node == it.node }
        }
        _navItemsFlow.emit(navItems)
    }

}