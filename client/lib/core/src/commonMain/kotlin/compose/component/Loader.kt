package compose.component

import DefaultPadding
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import compose.Transition
import core.remember.lazyRemember
import gamebook.client.lib.core.generated.resources.error_confirm
import gamebook.client.lib.core.generated.resources.error_message_generic
import gamebook.client.lib.core.generated.resources.error_message_internetConnection
import gamebook.client.lib.core.generated.resources.loading
import io.ktor.utils.io.errors.IOException
import rt


enum class LoaderViewState {
    Loading, Error, Content
}

val defaultLoadingBoxModifier = Modifier
    .fillMaxSize().background(Color.Black)


@Composable
fun <T> Loader(
    defaultValue: T,
    dataLoaded: T.() -> Boolean,
    load: suspend () -> T,
    onError: (() -> Unit)? = null,
    errorButtonText: String? = null,
    loadingText: String? = null,
    modifier: Modifier = defaultLoadingBoxModifier,
    errorMessage: @Composable (Throwable) -> String = {
        when (it) {
            is IOException -> rt { error_message_internetConnection }
            else -> rt { error_message_generic }
        }
    },
    customLoadingView: (@Composable BoxScope.(String) -> Unit)? = null,
    loadingTransition: Transition = Transition(),
    contentTransition: Transition = Transition(),
    errorTransition: Transition = Transition(),
    content: @Composable (T) -> Unit
) {
    var error by remember { mutableStateOf<Throwable?>(null) }
    val data = lazyRemember(defaultValue, { error = it }) { load() }
    val currentState = when {
        error != null -> LoaderViewState.Error
        data.value.dataLoaded() -> LoaderViewState.Content
        else -> LoaderViewState.Loading
    }
    AnimatedContent(
        currentState,
        transitionSpec = {
            when (targetState) {
                LoaderViewState.Loading -> loadingTransition
                LoaderViewState.Error -> errorTransition
                LoaderViewState.Content -> contentTransition
            }.transform
        }

    ) { state ->
        Box(Modifier.fillMaxSize()) {
            when (state) {
                LoaderViewState.Loading -> {
                    Box(modifier) {
                        val text = loadingText ?: rt { loading }
                        customLoadingView?.let {
                            with(this) {
                                it(text)
                            }
                        } ?: Text(
                            text,
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.Center)
                                .padding(DefaultPadding),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                LoaderViewState.Error -> {
                    if (error != null)
                        Box(modifier) {
                            Column(Modifier.align(Alignment.Center)) {
                                Text(
                                    errorMessage(error!!),
                                    modifier = Modifier
                                        .padding(DefaultPadding)
                                        .wrapContentHeight()
                                        .fillMaxWidth()
                                        .align(Alignment.CenterHorizontally),
                                    textAlign = TextAlign.Center
                                )
                                Button(
                                    {
                                        error = null
                                        (onError ?: data.reload)()
                                    },
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Text(errorButtonText ?: rt { error_confirm })
                                }
                            }
                        }
                }

                LoaderViewState.Content -> {
                    content(data.value)
                }
            }
        }
    }
}