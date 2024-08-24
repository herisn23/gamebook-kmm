package view

import DefaultPadding
import ScreenPadding
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import mrt

data class LoaderViewErrorHandler(
    val throwable: Throwable?,
    val showConfirmButton: Boolean = true,
    val buttonText: String? = null,
    val onErrorConfirm: () -> Unit
) {
    val hasError = throwable != null
}

operator fun Throwable?.invoke(
    confirmButtonText: String? = null,
    onErrorConfirm: (() -> Unit)? = null
) =
    LoaderViewErrorHandler(this, onErrorConfirm != null, confirmButtonText, onErrorConfirm ?: {})


@Composable
fun LoaderView(
    loaded: Boolean,
    errorHandler: LoaderViewErrorHandler,
    enterLoading: EnterTransition = fadeIn() + expandIn(),
    exitLoading: ExitTransition = shrinkOut() + fadeOut(),
    enterContent: EnterTransition = fadeIn() + expandIn(),
    exitContent: ExitTransition = shrinkOut() + fadeOut(),
    enterError: EnterTransition = fadeIn() + expandIn(),
    exitError: ExitTransition = shrinkOut() + fadeOut(),
    loadingContent: @Composable () -> Unit = {
        Text(
            "Loading", textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(DefaultPadding)
        )
    },
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    //Show error
    AnimatedVisibility(errorHandler.hasError, exit = exitError, enter = enterError) {
        Card(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(Modifier.padding(DefaultPadding)) {
                Text(
                    errorHandler.throwable?.message ?: "Undefined error",
                    modifier = Modifier.padding(DefaultPadding).weight(1f).wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
                Button(
                    errorHandler.onErrorConfirm,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(errorHandler.buttonText ?: mrt { error_confirm })
                }
            }
        }
    }

    //Show loading
    AnimatedVisibility(!loaded && !errorHandler.hasError, exit = exitLoading, enter = enterLoading) {
        loadingContent()
    }

    //Show content
    AnimatedVisibility(
        loaded && !errorHandler.hasError,
        content = content,
        exit = exitContent,
        enter = enterContent
    )
}