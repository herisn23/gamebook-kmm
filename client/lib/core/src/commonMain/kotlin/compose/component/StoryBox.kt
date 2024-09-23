package compose.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cardDefaultColors


@Composable
fun StoryBox(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        colors = cardDefaultColors,
        content = content,
        modifier = modifier
            .fillMaxWidth()
    )
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .background(Color.Red.copy(transparency))
//            .border(2.dp, Color.Magenta, shape = defaultRoundedShape)
//        ,
//        contentAlignment = contentAlignment,
//        propagateMinConstraints = propagateMinConstraints,
//        content = content
//    )
}