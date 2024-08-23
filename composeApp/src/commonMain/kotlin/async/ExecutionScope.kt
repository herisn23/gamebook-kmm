package async


typealias ExecutionScope<T> = (
    task: suspend () -> T,
    onSuccess: suspend (T) -> Unit,
    onFailure: (Throwable) -> Unit
) -> Unit

