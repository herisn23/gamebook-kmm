package cz.roldy.gb.story.localization

import cz.roldy.gb.story.model.Story


interface IStoryLocalizedContext {
    val story: Story
    val genus: Genus
    val locale: () -> String
}