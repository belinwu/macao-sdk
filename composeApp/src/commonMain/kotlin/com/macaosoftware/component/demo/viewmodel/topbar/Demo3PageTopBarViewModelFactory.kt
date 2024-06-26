package com.macaosoftware.component.demo.viewmodel.topbar

import com.macaosoftware.component.demo.viewmodel.topbar.Demo3PageTopBarViewModel
import com.macaosoftware.component.topbar.TopBarComponent
import com.macaosoftware.component.topbar.TopBarComponentViewModelFactory
import com.macaosoftware.component.topbar.TopBarStatePresenterDefault

class Demo3PageTopBarViewModelFactory(
    private val topBarStatePresenter: TopBarStatePresenterDefault,
    private val screenName: String,
    private val onDone: () -> Unit
) : TopBarComponentViewModelFactory<Demo3PageTopBarViewModel> {

    override fun create(
        topBarComponent: TopBarComponent<Demo3PageTopBarViewModel>
    ): Demo3PageTopBarViewModel {
        return Demo3PageTopBarViewModel(
            topBarComponent = topBarComponent,
            topBarStatePresenter = topBarStatePresenter,
            screenName = screenName,
            onDone = onDone
        )
    }
}
