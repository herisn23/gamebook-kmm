package compose.view.story.ingame

import HeaderSize
import StoryTileSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import compose.component.image.LazyImage
import core.http.cachedImage
import core.http.sac
import core.remember.getValue
import core.remember.lazyRemember
import cz.roldy.gb.story.model.Story
import defaultBackgroundColor
import gamebook.gameapp.generated.resources.Res
import gamebook.gameapp.generated.resources.story_default
import painter
import rp


@Composable
fun StoryTabsView(content: @Composable (Int) -> Unit) {
    var tabIndex by remember { mutableStateOf(0) }
    val tabs = listOf(Icons.Default.Home, Icons.Default.Settings)
    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.Black.copy(.5f),
        ) {
            tabs.forEachIndexed { index, icon ->
                Tab(selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    icon = {
                        Icon(
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                )
            }
        }

        content(tabIndex)
    }
}