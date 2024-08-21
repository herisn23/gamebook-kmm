inline fun <reified T> Any?.cast(): T {
    this is T
    return this as T
}
