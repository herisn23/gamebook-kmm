package compose

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith


data class Transition(
    val enter: EnterTransition = fadeIn(),
    val exit: ExitTransition = fadeOut(),
) {
    val transform: ContentTransform get() = enter.togetherWith(exit)
}