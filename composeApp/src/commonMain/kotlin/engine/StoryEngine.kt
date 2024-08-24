package engine

import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.localization.IStoryLocalizedContext
import cz.roldy.gb.story.localization.StringProvider
import cz.roldy.gb.story.localization.translate
import cz.roldy.gb.story.model.Story

class StoryEngine(
    override val story: Story,
    override val genus: Genus,
    override val locale: () -> String
) : IStoryLocalizedContext {

    /**
     * This function serves for localize Localized strings
     */
    fun t(block: StringProvider): String =
        block.translate(this)
}

fun Story.pickLanguage(selected: String) =
    when (supportedLanguages.contains(selected)) {
        true -> selected
        false -> {
            //if selected language is not supported try pick Czech
            if (supportedLanguages.contains("cs")) {
                "cs"
            } else {
                //if Czech language is not supported fallback to English
                "en"
            }
        }
    }
