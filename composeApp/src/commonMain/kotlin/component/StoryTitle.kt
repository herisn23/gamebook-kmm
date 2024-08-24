package component

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import bitmap
import component.image.LazyImage
import cz.roldy.gb.story.model.StoryMetadata
import defaultColors
import http.cachedImage
import http.sac
import mri
import story.name
import titleFont

@Composable
fun StoryTitle(metadata: StoryMetadata, content: @Composable () -> Unit = {}) {
    val defaultStoryImage = mri { story_default }
    LazyImage(
        defaultStoryImage,
        opacity = .7f,
        animationDuration = 500,
        imageScale = ContentScale.Crop,
        imageSource = {
            sac.cachedImage(metadata.id)?.bitmap
        }
    ) {
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
                    fontSize = 20.sp,
                    fontFamily = titleFont
                )
            }
        }
    }
}