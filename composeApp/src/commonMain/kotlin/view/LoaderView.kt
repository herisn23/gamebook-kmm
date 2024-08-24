package view

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
import mrs
import remember.lazyRemember
import screen.ScreenTransition


enum class LoaderViewState {
    Loading, Error, Content
}

val defaultLoadingBoxModifier = Modifier
    .fillMaxSize().background(Color.Black)


@Composable
fun <T> LoaderView(
    defaultValue: T,
    dataLoaded: T.() -> Boolean,
    load: suspend () -> T,
    onError: (() -> Unit)? = null,
    errorButtonText: String? = null,
    loadingText: String? = null,
    modifier: Modifier = defaultLoadingBoxModifier,
    customLoadingView: (@Composable BoxScope.(String) -> Unit)? = null,
    loadingTransition: ScreenTransition = ScreenTransition(),
    contentTransition: ScreenTransition = ScreenTransition(),
    errorTransition: ScreenTransition = ScreenTransition(),
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
                        val text = loadingText ?: mrs { loading }
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
                    Box(modifier) {
                        Column(Modifier.fillMaxSize()) {
                            Text(
                                error?.message ?: "Undefined error",
                                modifier = Modifier.padding(DefaultPadding).weight(1f)
                                    .wrapContentHeight(),
                                textAlign = TextAlign.Center
                            )
                            Button(
                                {
                                    error = null
                                    (onError ?: data.reload)()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(errorButtonText ?: mrs { error_confirm })
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