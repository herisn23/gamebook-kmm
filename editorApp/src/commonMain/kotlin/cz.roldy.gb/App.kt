package cz.roldy.gb

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

val mainScope = SharedScope(Dispatchers.Main)

val backgroundScope = SharedScope(Dispatchers.Default)

class SharedScope(private val context: CoroutineContext) : CoroutineScope {
    private val job = Job()
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Napier.e(throwable) { "[Coroutine Exception] $throwable" }
    }

    override val coroutineContext: CoroutineContext
        get() = context + job + exceptionHandler
}

suspend fun aha(): String {
    delay(1000)
    return "delayed"
}

fun runAha() =
    mainScope.launch {
        aha().let(::println)
    }


@Composable
fun App() {
    runAha()
    MaterialTheme {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button({ }) {
                Text("Click me!")
            }

        }
    }
}