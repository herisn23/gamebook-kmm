package engine

import androidx.compose.runtime.MutableState
import cz.roldy.gb.story.model.api.StorySection

data class StateDelegate(
    val section: MutableState<StorySection>
)