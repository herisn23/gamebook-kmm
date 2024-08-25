import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontFamily
import resources.Resources
import resources.loadFile
import resources.loadFont

@Composable
fun t(block: Resources.Strings.() -> String): String =
    Resources.Strings.block().let { key ->
        LocalStringsContext.current[key] ?: "_${key}_"
    }

@Composable
fun i(block: Resources.Images.() -> String): ImageBitmap =
    Resources.Images.block().let { key ->
        Resources.loadFile("images", key)!!.bitmap!!
    }

@Composable
fun f(block: Resources.Fonts.() -> String): FontFamily =
    Resources.Fonts.block().let { key ->
        Resources.loadFont(key)
    }
