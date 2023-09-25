package com.macaosoftware.component.demo.viewmodel.factory

import com.macaosoftware.component.demo.viewmodel.SettingsTopBarViewModel
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModel
import com.macaosoftware.component.topbar.TopBarComponentViewModelFactory
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class SettingsTopBarViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenterDefault,
    private val screenName: String,
    private val onDone: () -> Unit
) : TopBarComponentViewModelFactory<TopBarStatePresenterDefault> {

    override fun create(
        topBarComponent: TopBarComponent<TopBarStatePresenterDefault>
    ): TopBarComponentViewModel<TopBarStatePresenterDefault> {
        return SettingsTopBarViewModel(
            topBarComponent = topBarComponent,
            topBarStatePresenter = topBarStatePresenter,
            screenName = screenName,
            onDone = onDone
        )
    }
}