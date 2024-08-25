package component.image

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import gamebook.gameapp.generated.resources.OpenSans_Medium
import gamebook.gameapp.generated.resources.Res
import remember.getValue
import remember.lazyRemember

@Composable
fun LazyImage(
    defaultImage: Painter? = null,
    imageScale: ContentScale = ContentScale.None,
    modifier: BoxScope.() -> Modifier = { Modifier },
    opacity: Float = 1f,
    animationDuration: Int = 100,
    imageSource: suspend () -> Painter?,
    content: @Composable BoxScope.() -> Unit
) {
    val image by lazyRemember { imageSource() }
    val bgAlpha by animateFloatAsState(
        if (defaultImage != null && image == null) 0f else opacity,
        animationSpec = tween(animationDuration)
    )
    val imgAlpha by animateFloatAsState(
        if (image != null) opacity else 0f,
        animationSpec = tween(animationDuration)
    )
    Box(
        modifier = Modifier.run {
            if (image != null) {
                background(Color.Black)
            } else this
        }.fillMaxSize()
    ) {

        image.let { targetImage ->
            @Composable
            fun targetImage(bitmap: Painter, alpha: Float) =
                BackgroundImage(
                    bitmap,
                    modifier().alpha(alpha),
                    imageScale = imageScale
                ) { }
            if (defaultImage != null) {
                targetImage(defaultImage, opacity - bgAlpha)
            }
            if (targetImage != null) {
                targetImage(targetImage, imgAlpha)
            }
        }
        Res.font.OpenSans_Medium
        //always render content
        content()
    }
}