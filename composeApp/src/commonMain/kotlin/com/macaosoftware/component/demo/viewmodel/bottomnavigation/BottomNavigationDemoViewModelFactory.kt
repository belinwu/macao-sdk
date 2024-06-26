package com.macaosoftware.component.demo.viewmodel.bottomnavigation

import com.macaosoftware.component.demo.viewmodel.bottomnavigation.BottomNavigationDemoViewModel
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponentViewModelFactory
import com.macaosoftware.component.bottomnavigation.BottomNavigationComponent
import com.macaosoftware.component.bottomnavigation.BottomNavigationStatePresenterDefault

class BottomNavigationDemoViewModelFactory(
    private val bottomNavigationStatePresenter: BottomNavigationStatePresenterDefault
) : BottomNavigationComponentViewModelFactory<BottomNavigationDemoViewModel> {
    override fun create(
        bottomNavigationComponent: BottomNavigationComponent<BottomNavigationDemoViewModel>
    ): BottomNavigationDemoViewModel {
        return BottomNavigationDemoViewModel(bottomNavigationComponent, bottomNavigationStatePresenter)
    }
}
