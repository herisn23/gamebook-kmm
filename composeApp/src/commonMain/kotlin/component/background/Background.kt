package component.background

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import i

@Composable
fun Background(
    painter: Painter,
    content: @Composable BoxScope.() -> Unit
) =
    Box(
        modifier = with(Modifier) {
            fillMaxSize()
                .paint(
                    painter,
                    contentScale = ContentScale.FillBounds
                )

        },
        content = content
    )

@Composable
fun MainBackground(content: @Composable BoxScope.() -> Unit) =
    Background(i { main_background }, content)
