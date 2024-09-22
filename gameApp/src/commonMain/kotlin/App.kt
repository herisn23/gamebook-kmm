import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import compose.component.image.MainBackgroundImage
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import compose.router.StoriesRouter


@Composable
@Preview
fun App() {
    Napier.base(DebugAntilog())
    MaterialTheme(
        colorScheme = defaultColors,
        typography = defaultTypography
    ) {
        Scaffold { innerPadding ->
            Box(Modifier.padding(top = innerPadding.calculateTopPadding())) {
                MainBackgroundImage {
                    StoriesRouter()
                }
            }
        }

    }
}