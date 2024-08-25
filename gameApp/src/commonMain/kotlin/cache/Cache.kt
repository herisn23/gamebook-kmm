package cache

private val memoryCache = mutableMapOf<String, ByteArray>()

private val maxMemoryConsumption: Int = 100_000_000 //100 MB

enum class CacheType {
    Memory
}

suspend fun cache(
    id: String,
    type: CacheType = CacheType.Memory,
    force: Boolean = false,
    block: suspend () -> ByteArray?
) =
    when (type) {
        CacheType.Memory -> inMemory(id, force, block)
    }


private suspend fun inMemory(id: String, force: Boolean = false, block: suspend () -> ByteArray?) =
    when (memoryCache.containsKey(id) && !force) {
        true -> memoryCache[id]
        false -> block()?.also {
            if (isOutOfMemory(it)) {
                free(it.size)
            }
            memoryCache[id] = it
        }
    }

private fun free(min: Int) {
    //TODO free memory for new data by min
}

private fun sum() =
    memoryCache.map { it.value.size }.sum()

private fun isOutOfMemory(bytes: ByteArray) =
    sum() + bytes.size > maxMemoryConsumption
