package com.macaosoftware.component.panel

import com.macaosoftware.component.core.Component
import com.macaosoftware.component.core.NavigationComponent
import com.macaosoftware.component.core.NavigationComponentDefaults
import com.macaosoftware.component.stack.AddAllPushStrategy
import com.macaosoftware.component.stack.PushStrategy
import com.macaosoftware.component.viewmodel.ComponentViewModel
import com.macaosoftware.platform.CoroutineDispatchers

abstract class PanelComponentViewModel<T : PanelStatePresenter>(
    val panelComponent: PanelComponent<T>,
    private val lifecycleHandler: NavigationComponent.LifecycleHandler =
        NavigationComponentDefaults.createLifecycleHandler(),
    val dispatchers: CoroutineDispatchers = CoroutineDispatchers.Defaults,
    val pushStrategy: PushStrategy<Component> = AddAllPushStrategy(),
) : ComponentViewModel(),
    NavigationComponent.LifecycleHandler by lifecycleHandler {

    abstract fun onCreate()
    abstract val panelStatePresenter: T
}