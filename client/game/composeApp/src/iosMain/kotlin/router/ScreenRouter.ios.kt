package router

import androidx.compose.runtime.Composable

@Composable
actual fun <T> ComposeScreenDelegate<T>.invoke(scope: ScreenRouterScope<out Any?>) =
    compose.invoke(scope as ScreenRouterScope<T>)