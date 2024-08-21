package engine

import engine.localization.Genus
import engine.localization.StringProvider
import engine.localization.translate
import story.model.Story

class StoryEngine(
    val story: Story,
    val genus: Genus
) {

    /**
     * This function serves for localize Localized strings
     */
    fun t(block: StringProvider): String =
        block.translate(this)
}