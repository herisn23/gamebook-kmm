import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val ScreenPadding = 5.dp
val DefaultPadding = 10.dp
val StoryTileSize = 100.dp
val HeaderSize = 20.dp

val defaultRoundRadius = 10.dp

val defaultRoundedShape = RoundedCornerShape(defaultRoundRadius)

val bottomRoundedShape = RoundedCornerShape(0.dp, 0.dp, defaultRoundRadius, defaultRoundRadius)

val topRoundedShape = RoundedCornerShape(defaultRoundRadius, defaultRoundRadius, 0.dp, 0.dp)
