package engine.progress

import kotlinx.serialization.Serializable


@Serializable
data class StoryProgress(
    private val sections: MutableMap<Int, SectionProgress> = mutableMapOf()
) {
    fun addSection(id:Int, block: (SectionProgress?) -> SectionProgress) {
        val exist = sections[id]
        sections[id] = block(exist)
    }
    fun getSection(id: Int): SectionProgress? {
        return sections[id]
    }
}