package com.pablichj.templato.component.core

import com.pablichj.templato.component.core.stack.BackStack
import com.pablichj.templato.component.core.stack.StackTransition

internal fun ComponentWithBackStack.processBackstackEvent(
    event: BackStack.Event<Component>
): StackTransition<Component> {
    return when (event) {
        is BackStack.Event.Push -> {
            println("${getComponent().clazz}::Event.Push")
            val stack = event.stack
            if (stack.size > 1) {
                val newTop = stack[stack.lastIndex]
                val oldTop = stack[stack.lastIndex - 1]
                transitionInOut(newTop, oldTop)
            } else {
                transitionIn(stack[0]) // There is only one item in the stack so lastIndex == 0
            }
        }
        is BackStack.Event.Pop -> {
            println("${getComponent().clazz}::Event.Pop")
            val stack = event.stack
            val oldTop = event.oldTop
            if (stack.isNotEmpty()) {
                val newTop = stack[stack.lastIndex]
                transitionInOut(newTop, oldTop)
            } else {
                transitionOut(oldTop) // There is no items in the stack
            }
        }
        is BackStack.Event.PushEqualTop -> {
            println(
                "${getComponent().clazz}::Event.PushEqualTop()," +
                        " backStack.size = ${backStack.size()}"
            )
            StackTransition.InvalidPushEqualTop<Component>()
        }
        is BackStack.Event.PopEmptyStack -> {
            println("${getComponent().clazz}::Event.PopEmptyStack(), backStack.size = 0")
            StackTransition.InvalidPopEmptyStack<Component>()
        }
    }
}

private fun ComponentWithBackStack.transitionIn(newTop: Component) : StackTransition.In<Component>{
    println("${getComponent().clazz}::transitionIn(), newTop: ${newTop::class.simpleName}")
    newTop.dispatchStart()
    return StackTransition.In(newTop)
}

private fun ComponentWithBackStack.transitionInOut(
    newTop: Component,
    oldTop: Component
) : StackTransition.InOut<Component>{
    println(
        "${getComponent().clazz}::transitionInOut()," +
                " oldTop: ${oldTop::class.simpleName}, newTop: ${newTop::class.simpleName}"
    )
    // By convention always stop the previous top before starting the new one. TODO: Tests
    oldTop.dispatchStop()
    newTop.dispatchStart()
    return StackTransition.InOut(newTop, oldTop)
}

private fun ComponentWithBackStack.transitionOut(oldTop: Component): StackTransition.Out<Component> {
    println("${getComponent().clazz}::transitionOut(), oldTop: ${oldTop::class.simpleName}")
    oldTop.dispatchStop()
    return StackTransition.Out(oldTop)
}