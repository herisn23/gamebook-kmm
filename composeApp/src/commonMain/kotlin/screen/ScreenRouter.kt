package screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


interface Screen<T> {

    fun target(data: T): Target<T> =
        Target(data, this)

    data class Target<T>(
        val data: T,
        val id: Screen<T>
    )
}


typealias ComposeScreen<T> = @Composable (ScreenRouterScope<T>) -> Unit

private val defaultEnter = fadeIn() + expandIn()
private val defaultExit = shrinkOut() + fadeOut()

data class ScreenRouterScope<T>(
    val data: T,
    private val _navigate: (Screen.Target<*>, EnterTransition, ExitTransition) -> Unit
) {
    fun <E> navigate(
        id: Screen.Target<E>,
        enter: EnterTransition = defaultEnter,
        exit: ExitTransition = defaultExit
    ) =
        _navigate(id, enter, exit)
}

data class CurrentScreen(
    val target: Screen.Target<*>,
    val enter: EnterTransition = defaultEnter,
    val exit: ExitTransition = defaultExit
)

class ScreenRoutingContent(
    internal val mapping: MutableMap<Screen<*>, ComposeScreen<*>>
) {

    fun <T : Any?> add(id: Screen<T>, content: ComposeScreen<T>) {
        mapping[id] = content as ComposeScreen<*>
    }

}

@Composable
fun <T> ScreenRouter(
    defaultScreen: Screen<T?>,
    modifier: Modifier = Modifier,
    build: ScreenRoutingContent.() -> Unit
) = ScreenRouter(defaultScreen, null, modifier, build)

@Composable
fun <T> ScreenRouter(
    defaultScreen: Screen<T>,
    defaultData: T,
    modifier: Modifier = Modifier,
    build: ScreenRoutingContent.() -> Unit
) {
    val mapping by remember {
        mutableStateOf(
            ScreenRoutingContent(mutableMapOf()).apply {
                build()
                if (!mapping.containsKey(defaultScreen)) {
                    throw Exception("defaultScreen $defaultScreen is not configured")
                }
            }.mapping
        )
    }

    var currentScreen by remember { mutableStateOf(CurrentScreen(defaultScreen.target(defaultData))) }


    AnimatedContent(
        currentScreen,
        transitionSpec = {
            (currentScreen.enter).togetherWith(currentScreen.exit)
        },
        modifier = modifier
    ) { state ->
        val content =
            mapping[state.target.id]
                ?: throw Exception("screen ${state.target.id} is not configured")
        content(
            ScreenRouterScope(
                state.target.data
            ) { target, enter, exit ->
                currentScreen = CurrentScreen(target, enter, exit)
            }
        )
    }
}