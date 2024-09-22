package compose.router

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier


@Composable
expect fun <T> ComposeScreenDelegate<T>.invoke(scope: ScreenRouterScope<out Any?>)


interface Screen<T> {

    fun target(data: T): Target<T> =
        Target(data, this)

    data class Target<T>(
        val data: T,
        val id: Screen<T>
    )
}

data class ComposeScreenDelegate<T>(
    val compose: ComposeScreen<T>
)

typealias ComposeScreen<T> = @Composable (ScreenRouterScope<T>) -> Unit

data class ScreenTransition(
    val enter: EnterTransition = fadeIn(),
    val exit: ExitTransition = fadeOut(),
) {
    val transform: ContentTransform get() = enter.togetherWith(exit)
}

data class EnterTransitionWrapper(
    val enter: EnterTransition
)

infix fun EnterTransition.exit(exit: ExitTransition) =
    ScreenTransition(this, exit)

infix fun Any?.enter(next: EnterTransition) = next

infix fun EnterTransitionWrapper.exit(exit: ExitTransition) =
    ScreenTransition(this.enter, exit)

infix operator fun EnterTransition.plus(next: EnterTransition) =
    EnterTransitionWrapper(this + next)

infix fun ExitTransition.next(next: ExitTransition) =
    this + next


data class ScreenRouterScope<T>(
    val data: T,
    val scope: AnimatedContentScope,
    private val _navigate: (Screen.Target<*>, ScreenTransition) -> Unit
) {
    fun <E> navigate(
        id: Screen.Target<E>,
        transition: ScreenTransition = ScreenTransition()
    ) =
        _navigate(id, transition)
}

data class CurrentScreen(
    val target: Screen.Target<*>,
    val transition: ScreenTransition = ScreenTransition()
)

class ScreenRoutingContent(
    internal val mapping: MutableMap<Screen<*>, ComposeScreenDelegate<*>>
) {

    fun <T : Any?> add(id: Screen<T>, content: ComposeScreen<T>) {
        mapping[id] = ComposeScreenDelegate(content)
    }

}

@Composable
fun <T> ScreenRouter(
    defaultScreen: Screen<T?>,
    defaultTransition: ScreenTransition = ScreenTransition(),
    modifier: Modifier = Modifier,
    build: ScreenRoutingContent.() -> Unit
) = ScreenRouter(defaultScreen, defaultTransition, null, modifier, build)

@Composable
fun <T> ScreenRouter(
    defaultScreen: Screen<T>,
    defaultTransition: ScreenTransition = ScreenTransition(),
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

    var currentScreen by remember {
        mutableStateOf(
            CurrentScreen(
                defaultScreen.target(defaultData),
                defaultTransition
            )
        )
    }


    AnimatedContent(
        currentScreen,
        transitionSpec = {
            targetState.transition.enter togetherWith targetState.transition.exit
        },
        modifier = modifier,
        contentKey = { it.target.id }
    ) { state ->
        val scope = this
        val content =
            mapping[state.target.id]
                ?: throw Exception("screen ${state.target.id} is not configured")
        Box(Modifier.fillMaxSize()) {
            val scope = ScreenRouterScope(
                state.target.data,
                scope
            ) { target, transition ->
                currentScreen = CurrentScreen(target, transition)
            }
            content.invoke(scope as ScreenRouterScope<out Any?>)
        }
    }
}