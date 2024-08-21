package remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.node.Ref
import async.onBackground
import async.onMain
import async.onView
import io.github.aakira.napier.Napier

typealias ExecutionScope<T> = (
    task: suspend () -> T,
    onSuccess: suspend (T) -> Unit,
    onFailure: (Throwable) -> Unit
) -> Unit


/**
 * Executes long running task on background scope but returns value to main scope
 */
fun <T> onView(): ExecutionScope<T> = ::onView

/**
 * Executes long running task and his return value on background scope
 */
fun <T> onBackground(): ExecutionScope<T> = ::onBackground

/**
 * Executes long running task and his return value on main scope
 */
fun <T> onMain(): ExecutionScope<T> = ::onMain


@Composable
inline fun <T> lazyRemember(
    crossinline onError: (Throwable) -> Unit = {},
    crossinline initializer: suspend () -> T
) = lazyRemember(onView(), null, onError, initializer)

@Composable
inline fun <T> lazyRemember(
    default: T,
    crossinline onError: (Throwable) -> Unit = {},
    crossinline initializer: suspend () -> T
) = lazyRemember(onView(), default, onError, initializer)

@Composable
inline fun <T> lazyRemember(
    execution: ExecutionScope<T>,
    default: T,
    crossinline onError: (Throwable) -> Unit,
    crossinline initialize: suspend () -> T
): MutableState<T> {
    val state = remember { mutableStateOf(default) }
    val initialized = remember { Ref<Boolean>() }
    if (initialized.value == null) {
        execution(
            { initialize() },
            {
                state.value = it
                initialized.value = true
            },
            {
                initialized.value = true
                Napier.e(it) { "Error occurred when lazyRemembering" }
                onError(it)
            }
        )
    }

    return state
}