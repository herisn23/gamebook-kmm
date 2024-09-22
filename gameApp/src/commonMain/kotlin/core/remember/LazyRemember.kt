package core.remember

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import core.async.ExecutionScope
import core.async.onBackground
import core.async.onMain
import core.async.onView
import io.github.aakira.napier.Napier
import kotlin.reflect.KProperty


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

data class LazyRememberInitialized(
    var initialized: Boolean = false
)

data class LazyState<T>(
    private var state: State<T>,
    val reload: () -> Unit,
) {
    val value get() = state.value
}

operator fun <T> LazyState<T>.getValue(thisObj: Any?, property: KProperty<*>): T = value

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
): LazyState<T> {
    val state = remember { mutableStateOf(default) }
    val initialized = remember { mutableStateOf(LazyRememberInitialized()) }
    val lazyState = remember {
        LazyState(state) {
            //reload
            initialized.value = LazyRememberInitialized()
        }
    }
    if (!initialized.value.initialized) {
        execution(
            { initialize() },
            {
                state.value = it
                //avoid re-render
                initialized.value.initialized = true
            },
            {
                //avoid re-render
                initialized.value.initialized = true
                Napier.e(it) { "Error occurred when lazyRemembering" }
                onError(it)
            }
        )
    }
    return lazyState
}