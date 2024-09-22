package engine

import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import cz.roldy.gb.story.localization.StringProvider
import cz.roldy.gb.story.localization.translate

val LocalStoryEngine = compositionLocalOf<StoryEngine?> { null }

val StoryEngineContext
    @Composable
    get() = LocalStoryEngine.current!!


/**
 * This function serves for localize story localized strings such as section texts, options, battles, etc.
 */
@Composable
fun t(trim: Boolean = true, block: StringProvider<StoryEngine>): String =
    block.translate(StoryEngineContext).let {
        if (trim) it.trim() else it
    }