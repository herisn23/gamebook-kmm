package screen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.NavType
import com.mikepenz.markdown.m3.Markdown
import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.localization.invoke
import cz.roldy.gb.story.model.StoryMetadata
import engine.StoryEngine
import remember.lazyRemember
import routing.LocalMainNav
import routing.RouteBuilder
import routing.RoutePathVars
import story.loadStory
import view.LoaderView
import view.invoke

interface StoryVars : RoutePathVars {
    val id: String
}

val StoryRoute by lazy {
    RouteBuilder<StoryVars>()
        .addPathVariable("storyLoader")
        .addPathVariable(StoryVars::id, NavType.StringType)
        .build()
}

@Composable
fun StoryScreen(metadata: StoryMetadata) {
    val navigate = LocalMainNav.current
    var loadingError by remember { mutableStateOf<Throwable?>(null) }
    val story by lazyRemember({ loadingError = it }) { loadStory(metadata) }
    var selected by remember { mutableStateOf(true) }

    LoaderView(story != null, loadingError {
        navigate {
            it.popBackStack(StoriesRoute.route, false)
        }
    }) {
        Column {
            val engine =
                StoryEngine(story!!, Genus.Masculine.takeIf { selected } ?: Genus.Feminine) {
                    Locale.current.language
                }
            Text(engine.t { text() })
            val a = engine.t {
                compositeText { selector ->
                    "ABC"
                }
            }
//            Image(i { main_background }, "desc")
            Markdown(a)
            Button({
                selected = !selected
            }) {
                Text("Klik")
            }
            Button({
                navigate {
                    it.popBackStack()
                }
            }) {
                Text("Back")
            }
        }
    }
}