package compose.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun StoryText(text:String, modifier: Modifier = Modifier) {
    Text(text, color = MaterialTheme.colorScheme.onPrimary, modifier = modifier)
}