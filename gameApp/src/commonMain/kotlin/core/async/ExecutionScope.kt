package core.async

import androidx.compose.runtime.DisallowComposableCalls


typealias ExecutionScope<T> = @DisallowComposableCalls (
    task: suspend () -> T,
    onSuccess: suspend (T) -> Unit,
    onFailure: (Throwable) -> Unit
) -> Unit

