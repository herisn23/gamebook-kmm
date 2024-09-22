package view.story

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
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