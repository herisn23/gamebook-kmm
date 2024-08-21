package routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import remember.lazyRemember
import screen.StoriesRoute
import screen.StoriesScreen
import screen.StoryPickRoute
import screen.StoryPickScreen
import screen.StoryPickVars
import screen.StoryRoute
import screen.StoryScreen
import screen.StoryVars
import story.loadStories
import view.LoaderView
import view.invoke

typealias NavigateLambda = (NavHostController) -> Unit

val LocalMainNav = compositionLocalOf<(NavigateLambda) -> Unit> { {} }


@Composable
fun MainRouting() {
    val navController = rememberNavController()
    var loadingError by remember { mutableStateOf<Throwable?>(null) }
    val stories by lazyRemember(emptyList(), { loadingError = it }) { loadStories() }

    LoaderView(stories.isNotEmpty(), loadingError {
        navController.popBackStack(StoriesRoute.route, false)
    }) {
        CompositionLocalProvider(LocalMainNav.provides { it(navController) }) {
            NavHost(
                navController,
                startDestination = StoriesRoute.route
            ) {
                StoriesRoute.composable(this) {
                    StoriesScreen(stories) { metadata ->
                        StoryPickRoute
                            .navigate(navController) {
                                add(StoryPickVars::id, metadata.id)
                            }
                    }
                }

                StoryPickRoute.composable(this) {
                    val id: String by it
                    StoryPickScreen(
                        stories.first { m -> m.id == id },
                        onBack = navController::popBackStack,
                        onStoryPick = { metadata->
                            StoryRoute
                                .navigate(navController) {
                                    add(StoryVars::id, metadata.id)
                                }
                        }
                    )
                }

                StoryRoute.composable(this) {
                    val id: String by it
                    StoryScreen(stories.first { m -> m.id == id })
                }
            }
        }

    }

}