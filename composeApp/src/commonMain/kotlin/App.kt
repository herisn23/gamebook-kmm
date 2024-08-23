import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import cache.imageCache
import component.background.MainBackground
import http.storyApiClient
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import remember.lazyRemember


@Composable
@Preview
fun App() {

    Napier.base(DebugAntilog())
// Could be OkioFileKache or JavaFileKache

    val image by lazyRemember {
        imageCache("zombie_apocalypse") {
            storyApiClient.image("zombie_apocalypse")
        }?.bitmap
    }

    MaterialTheme(
        colorScheme = darkColorScheme()
    ) {
        MainBackground {
            image?.let {
                Image(it, "")
            }
//            MainRouting()
        }
    }
}