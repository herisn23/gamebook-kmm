package component.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import gamebook.gameapp.generated.resources.Res
import gamebook.gameapp.generated.resources.main_background
import org.jetbrains.compose.resources.painterResource

@Composable
fun BackgroundImage(
    painter: Painter,
    modifier: Modifier = Modifier,
    imageScale: ContentScale = ContentScale.FillBounds,
    content: @Composable BoxScope.() -> Unit
) =
    Box(
        modifier = with(modifier) {
            fillMaxSize()
                .paint(
                    painter,
                    contentScale = imageScale
                )

        },
        content = content
    )

@Composable
fun MainBackgroundImage(content: @Composable BoxScope.() -> Unit) =
    BackgroundImage(painterResource(Res.drawable.main_background), content = content)