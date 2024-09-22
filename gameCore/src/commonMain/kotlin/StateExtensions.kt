import androidx.compose.runtime.mutableStateOf


val <T> T.toState get() = mutableStateOf(this)