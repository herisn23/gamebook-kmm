package compose.view.story.ingame

import DefaultPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import bottomRoundedShape
import compose.component.StoryBox
import compose.component.StoryButton
import compose.component.StoryText
import cz.roldy.gb.story.localization.invoke
import cz.roldy.gb.story.model.api.StoryOption
import cz.roldy.gb.story.model.api.StorySection
import defaultRoundedShape
import engine.StoryEngineContext
import engine.progress.SectionProgress
import engine.t
import topRoundedShape


@Composable
fun StorySectionView() {
    val engine = StoryEngineContext

    SectionView(
        section = engine.currentSection,
        progress = engine.progress.getSection(engine.currentSection.id),
        onOptionSelected = { option ->
            engine.nextSection(option.section) {
                it ?: SectionProgress(option.section)
            }
        },
        onCombatStart = {

        }
    )

}

@Composable
internal fun SectionView(
    section: StorySection,
    progress: SectionProgress?,
    onOptionSelected: (StoryOption) -> Unit,
    onCombatStart: () -> Unit
) {
    LazyColumn(state = LazyListState(), modifier = Modifier.fillMaxHeight()) {
        item {
            StoryBox(modifier = Modifier.fillParentMaxHeight()) {

                StoryText(t { section.sectionText(progress)() }, modifier = Modifier.padding(DefaultPadding))
                Icon(
                    Icons.Default.ArrowDropDown, "Show options",
                    modifier = Modifier.align(Alignment.BottomCenter),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        nextSectionOptions(section, onOptionSelected)
    }
}

private fun StorySection.sectionText(progress: SectionProgress?) =
    progress?.let { visitedText } ?: text

fun LazyListScope.nextSectionOptions(section: StorySection, onClick: (StoryOption) -> Unit) {
    itemsIndexed(section.options) { index, option ->
        StoryButton(
            onClick = { onClick(option) },
            modifier = Modifier.fillMaxWidth(),
            shape = run {
                when (index) {
                    0 -> if (section.options.size == 1) defaultRoundedShape else topRoundedShape
                    section.options.size - 1 -> bottomRoundedShape
                    else -> RectangleShape
                }
            }) {
            Text(t { option.text() })
        }
    }
}