package view.story

import DefaultPadding
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import cachedImage
import compose.component.image.BackgroundImage
import compose.component.image.LazyImage
import core.story.name
import cz.roldy.gb.story.model.StoryMetadata
import defaultColors
import gamebook.client.lib.core.generated.resources.story_default
import http.sac
import painter
import rp

@Composable
fun StoryTitleView(
    metadata: StoryMetadata,
    preloadedImage: Painter? = null,
    onImageLoaded: (Painter) -> Unit = {},
    content: @Composable () -> Unit = {}
) {

    @Composable
    fun innerContent() =
        BoxWithConstraints(
            Modifier
                .fillMaxSize()
        ) {
            content()
            Box(
                Modifier
                    .size(
                        width = maxWidth,
                        height = maxHeight / 2,
                    )
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = .5f))
            ) {
                Text(
                    text = metadata.name,
                    color = defaultColors.onPrimary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(DefaultPadding),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            }
        }
    if (preloadedImage != null) {
        BackgroundImage(
            preloadedImage,
            Modifier.alpha(.7f),
            imageScale = ContentScale.Crop
        ) { }
        innerContent()
    } else {
        LazyImage(
            rp { story_default },
            opacity = .7f,
            animationDuration = 500,
            imageScale = ContentScale.Crop,
            imageSource = {
                sac.cachedImage(metadata.id)?.painter?.also(onImageLoaded)
            }
        ) {
            innerContent()
        }
    }

}