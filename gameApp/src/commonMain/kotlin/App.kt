import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import component.image.MainBackgroundImage
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.jetbrains.compose.ui.tooling.preview.Preview
import remember.getValue
import remember.lazyRemember
import resources.Resources
import resources.loadStrings
import screen.story.StoriesRouter


data class LocalizationContext(
    private val setLocale: (Locale) -> Unit,
    private val getLocale: () -> Locale
) {
    var current: Locale
        get() = getLocale()
        set(value) {
            setLocale(value)
        }
}

val LocalStringsContext = compositionLocalOf<Map<String, String>> { emptyMap() }
val LocalLocalizationContext =
    compositionLocalOf { LocalizationContext({}, { Locale.current }) }
val L get() = LocalLocalizationContext


@Composable
@Preview
fun App() {

    Napier.base(DebugAntilog())
    val strings by lazyRemember { Resources.loadStrings(listOf("base", "en")) }
    var currentLocale by remember { mutableStateOf(Locale.current) }

    CompositionLocalProvider(
        LocalLocalizationContext.provides(
            LocalizationContext({ currentLocale = it }, { currentLocale })
        )
    ) {
        strings?.let { s ->
            val currentLangSet = s[currentLocale.language] ?: s["base"] ?: emptyMap()
            CompositionLocalProvider(LocalStringsContext.provides(currentLangSet)) {
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
        }
    }
}