package engine

import cz.roldy.gb.story.localization.Genus
import cz.roldy.gb.story.localization.IStoryLocalizedContext
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.api.StorySection
import engine.progress.SectionProgress
import engine.progress.StoryProgress


class StoryEngine(
    val progress: StoryProgress,
    val state: StateDelegate,
    override val story: Story,
    override val genus: Genus,
    override val locale: () -> String,
) : IStoryLocalizedContext {

    val currentSection: StorySection
        get() = state.section.value


    fun nextSection(id:Int, block: (SectionProgress?) -> SectionProgress) {
        progress.addSection(id, block)
        state.section.value = story.definition.findSection(id)
    }

//    var section: Int
//        get() = state.section.value.id
//        set(value) {
//            state.section.value = story.definition.findSection(value)
//        }
}