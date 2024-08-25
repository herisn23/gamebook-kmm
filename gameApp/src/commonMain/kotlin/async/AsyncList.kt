package async

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun <T, R> List<T>.mapAsync(block: suspend (T) -> R) =
    coroutineScope {
        awaitAll(*map { async { block(it) } }.toTypedArray())
    }