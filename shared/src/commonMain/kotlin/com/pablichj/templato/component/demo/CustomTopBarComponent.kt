package com.pablichj.templato.component.demo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import com.pablichj.templato.component.core.Component
import com.pablichj.templato.component.core.router.DeepLinkMatchData
import com.pablichj.templato.component.core.router.DeepLinkMatchType
import com.pablichj.templato.component.core.stack.StackBarItem
import com.pablichj.templato.component.core.stack.StackComponent

class CustomTopBarComponent(
    val screenName: String,
    config: Config,
    val onMessage: (Msg) -> Unit
) : StackComponent(config) {

    val Step1 = SimpleComponent(
        "$screenName / Page 1",
        Color.Yellow
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                backStack.push(Step2)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
    }

    val Step2 = SimpleComponent(
        "$screenName / Page 2",
        Color.Green
    ) { msg ->
        when (msg) {
            SimpleComponent.Msg.Next -> {
                backStack.push(Step3)
            }
        }
    }.also {
        it.setParent(this@CustomTopBarComponent)
    }

    val Step3 =
        SimpleComponent(
            "$screenName / Page 3",
            Color.Cyan
        ) { msg ->
            when (msg) {
                SimpleComponent.Msg.Next -> {
                    onMessage(Msg.OnboardDone)
                    //router?.handleDeepLink("Settings")
                }
            }
        }.also {
            it.setParent(this@CustomTopBarComponent)
        }

    override fun onStart() {
        println("CustomTopBarComponent::start()")
        if (activeComponent.value == null) {
            backStack.push(Step1)
        } else {
            activeComponent.value?.dispatchStart()
        }
    }

    override fun getStackBarItemFromComponent(component: Component): StackBarItem {
        return when (component) {
            Step1 -> {
                StackBarItem(
                    Step1.text,
                    Icons.Filled.Star,
                )
            }

            Step2 -> {
                StackBarItem(
                    Step2.text,
                    Icons.Filled.Star,
                )
            }

            Step3 -> {
                StackBarItem(
                    Step3.text,
                    Icons.Filled.Star,
                )
            }

            else -> {
                throw IllegalStateException()
            }
        }
    }

    // region: DeepLink

    override fun getDeepLinkHandler(): DeepLinkMatchData {
        return DeepLinkMatchData(
            screenName,
            DeepLinkMatchType.MatchOne
        )
    }

    override fun getChildForNextUriFragment(nextUriFragment: String): Component? {
        return when (nextUriFragment) {
            "Page1" -> Step1
            "Page2" -> Step2
            "Page3" -> Step3
            else -> null
        }
    }

    // endregion

    sealed interface Msg {
        object OnboardDone : Msg
    }

}