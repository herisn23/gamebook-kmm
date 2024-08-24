package component.image

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import remember.lazyRemember
import view.LoaderView
import view.invoke


@Composable
fun LazyImage(imageSource: suspend () -> ImageBitmap) =
    LazyImage(null, imageSource)

@Composable
fun LazyImage(defaultImage: ImageBitmap?, imageSource: suspend () -> ImageBitmap) {
    var error by remember { mutableStateOf<Throwable?>(null) }
    val image by lazyRemember(defaultImage, { error = it }) { imageSource() }
    LoaderView(image != null, error(), loadingContent = {
        if (defaultImage != null)
            Image(defaultImage, "desc")
    }) {
        Image(image!!, "desc")
    }
}


@Composable
fun LazyBackgroundImage(
    defaultImage: ImageBitmap? = null,
    imageScale: ContentScale = ContentScale.FillBounds,
    opacity: Float = 1f,
    animationDuration: Int = 100,
    imageSource: suspend () -> ImageBitmap?,
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

        image.let { targetState ->
            @Composable
            fun targetImage(bitmap: ImageBitmap, alpha: Float) =
                Image(
                    bitmap,
                    contentDescription = "",
                    contentScale = imageScale, // or some other scale
                    modifier = Modifier.matchParentSize().alpha(alpha)
                )
            if (defaultImage != null) {
                targetImage(defaultImage, opacity - bgAlpha)
            }
            if (targetState != null) {
                targetImage(targetState, imgAlpha)
            }
        }

        //always render content
        content()
    }
}