package engine.progress

import kotlinx.serialization.Serializable

@Serializable
data class SectionProgress(
    val id: Int,
    val enemiesDefeated: Boolean = false,
)