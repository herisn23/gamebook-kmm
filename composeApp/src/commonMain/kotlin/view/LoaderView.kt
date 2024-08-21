package view

import DefaultPadding
import ScreenPadding
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
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
import t

data class LoaderViewErrorHandler(
    val throwable: Throwable?,
    val buttonText: String? = null,
    val onErrorConfirm: () -> Unit
) {
    val hasError = throwable != null
}

operator fun Throwable?.invoke(onErrorConfirm: () -> Unit) =
    LoaderViewErrorHandler(this, onErrorConfirm = onErrorConfirm)


@Composable
fun LoaderView(
    loaded: Boolean,
    errorHandler: LoaderViewErrorHandler,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    //Show error
    AnimatedVisibility(errorHandler.hasError) {
        Column(
            modifier = Modifier.fillMaxSize().padding(ScreenPadding)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Text(
                    errorHandler.throwable?.message ?: "Undefined error",
                    modifier = Modifier.padding(DefaultPadding).fillMaxSize().wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
            Button(
                errorHandler.onErrorConfirm,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(errorHandler.buttonText ?: t { error_confirm })
            }
        }
    }

    //Show loading
    AnimatedVisibility(!loaded && !errorHandler.hasError) {
        Text(
            "Loading", textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .padding(DefaultPadding)
        )
    }

    //Show content
    AnimatedVisibility(loaded && !errorHandler.hasError, content = content)
}