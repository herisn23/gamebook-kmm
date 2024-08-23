package cache

expect suspend fun exist(fileName: String): Boolean
expect suspend fun save(fileName: String, bytes: ByteArray): ByteArray?
expect suspend fun load(fileName: String): ByteArray?


suspend fun imageCache(fileName: String, force:Boolean = false, block: suspend () -> ByteArray?) =
    when (exist(fileName) && !force) {
        true -> load(fileName)
        false -> block()?.let { save(fileName, it) }
    }

//    FileKache(directory = "cache", maxSize = 100 * 1024 * 1024) {
//        // Other optional configurations
//        strategy = KacheStrategy.MRU
//        // ...
//    }.getOrPut(id, creationFunction)