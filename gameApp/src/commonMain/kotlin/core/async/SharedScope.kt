package core.async

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

/**
 * This function calls suspend function on backgroundScope and return value to mainScope
 */
fun <T> onView(
    task: suspend () -> T,
    onSuccess: suspend (T) -> Unit,
    onFailure: (Throwable) -> Unit = {}
) {
    backgroundScope.launch {
        val result = runCatching {
            task()
        }
        mainScope.launch {
            result.fold({ onSuccess(it) }, onFailure)
        }
    }
}

/**
 * This function calls suspend function on backgroundScope
 */
fun <T> onBackground(
    task: suspend () -> T,
    onSuccess: suspend (T) -> Unit,
    onFailure: (Throwable) -> Unit = {}
) {
    backgroundScope.launch {
        runCatching {
            task()
        }.fold({ onSuccess(it) }, onFailure)
    }
}

/**
 * This function calls suspend function on mainScope
 */
fun <T> onMain(
    task: suspend () -> T,
    onSuccess: suspend (T) -> Unit,
    onFailure: (Throwable) -> Unit = {}
) {
    mainScope.launch {
        runCatching {
            task()
        }.fold({ onSuccess(it) }, onFailure)
    }
}
