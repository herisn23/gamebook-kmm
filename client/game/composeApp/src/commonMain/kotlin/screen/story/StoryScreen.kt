package screen.story

import DefaultPadding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.intl.Locale
import compose.view.StoryGameView
import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.api.StoryCharacter
import engine.LocalStoryEngine
import engine.StateDelegate
import engine.StoryEngine
import engine.pickLanguage
import engine.progress.StoryProgress
import router.Screen
import toState
import view.story.StorySettingsView
import view.story.StoryTabsView


data object StoryScreen : Screen<StoryScreenDefaults>

data class StoryScreenDefaults(
   val story: Story,
   val character: StoryCharacter,
   val genus: Genus
)

@Composable
fun StoryScreen(defaults: StoryScreenDefaults) {
   val (story, character, genus) = defaults
   Box(Modifier.background(Color.Black.copy(.5f)).fillMaxHeight()) {
      StoryTabsView {
         val locale by remember { story.pickLanguage(Locale.current.language).toState }
         val section = remember { story.definition.findSection(character.startSection).toState }

         val engine by remember {
            mutableStateOf(
               StoryEngine(
                  StoryProgress(),
                  StateDelegate(section),
                  story,
                  genus
               ) { locale }
            )
         }

         Box(Modifier.padding(DefaultPadding)) {
            CompositionLocalProvider(LocalStoryEngine.provides(engine)) {
               when (it) {
                  0 -> StoryGameView()
                  1 -> StorySettingsView()
               }
            }
         }
      }
   }
}