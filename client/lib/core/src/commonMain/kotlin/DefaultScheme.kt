import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import gamebook.client.lib.core.generated.resources.Matemasie_Regular
import gamebook.client.lib.core.generated.resources.OpenSans_Medium
import gamebook.client.lib.core.generated.resources.Res
import org.jetbrains.compose.resources.Font

val defaultColors: ColorScheme =
    ColorScheme(
        primary = "#493217".toColor,//5C4418
        onPrimary = "#eecfaa".toColor,
        primaryContainer = "#00138b".toColor,
        onPrimaryContainer = "#00a2a4".toColor,
        inversePrimary = "#b38f59".toColor,
        secondary = "#CCC2DCFF".toColor,
        onSecondary = "#332D41FF".toColor,
        secondaryContainer = "#4A4458FF".toColor,
        onSecondaryContainer = "#E8DEF8FF".toColor,
        tertiary = "#EFB8C8FF".toColor,
        onTertiary = "#492532FF".toColor,
        tertiaryContainer = "#633B48FF".toColor,
        onTertiaryContainer = "#FFD8E4FF".toColor,
        background = "#1C1B1FFF".toColor,
        onBackground = "#E6E1E5FF".toColor,
        surface = "#1C1B1FFF".toColor,
        onSurface = "#E6E1E5FF".toColor,
        surfaceVariant = "#00000099".toColor,
        onSurfaceVariant = "#CAC4D0FF".toColor,
        surfaceTint = "#D0BCFFFF".toColor,
        inverseSurface = "#E6E1E5FF".toColor,
        inverseOnSurface = "#313033FF".toColor,
        error = "#F2B8B5FF".toColor,
        onError = "#601410FF".toColor,
        errorContainer = "#8C1D18FF".toColor,
        onErrorContainer = "#F9DEDCFF".toColor,
        outline = "#938F99FF".toColor,
        outlineVariant = "#49454FFF".toColor,
        scrim = "#000000FF".toColor,
        surfaceBright = "#313033FF".toColor,
        surfaceDim = "#141317FF".toColor,
        surfaceContainer = "#201F23FF".toColor,
        surfaceContainerHigh = "#2B292DFF".toColor,
        surfaceContainerHighest = "#313033FF".toColor,
        surfaceContainerLow = "#1C1B1FFF".toColor,
        surfaceContainerLowest = "#0E0E11FF".toColor
    )
val defaultBackgroundColor = Color.Black.copy(alpha = 0.5f)


val cardDefaultColors
    @Composable
    get() = CardDefaults.cardColors(
        containerColor = defaultBackgroundColor
    )

val defaultFont: FontFamily
    @Composable get() =
        FontFamily(Font(Res.font.OpenSans_Medium))

val titleFont: FontFamily
    @Composable get() =
        FontFamily(Font(Res.font.Matemasie_Regular))


val defaultTypography
    @Composable
    get() = run {
        val defaultFont = defaultFont
        typography.copy(
            displayLarge = typography.displayLarge.copy(fontFamily = defaultFont),
            displayMedium = typography.displayMedium.copy(fontFamily = defaultFont),
            displaySmall = typography.displaySmall.copy(fontFamily = defaultFont),
            headlineLarge = typography.headlineLarge.copy(fontFamily = defaultFont),
            headlineMedium = typography.headlineMedium.copy(fontFamily = defaultFont),
            headlineSmall = typography.headlineSmall.copy(fontFamily = defaultFont),
            titleLarge = typography.titleLarge.copy(fontFamily = defaultFont),
            titleMedium = typography.titleMedium.copy(fontFamily = defaultFont),
            titleSmall = typography.titleSmall.copy(fontFamily = defaultFont),
            bodyLarge = typography.bodyLarge.copy(fontFamily = defaultFont),
            bodyMedium = typography.bodyMedium.copy(fontFamily = defaultFont),
            bodySmall = typography.bodySmall.copy(fontFamily = defaultFont),
            labelLarge = typography.labelLarge.copy(fontFamily = defaultFont),
            labelMedium = typography.labelMedium.copy(fontFamily = defaultFont),
            labelSmall = typography.labelSmall.copy(fontFamily = defaultFont)
        )
    }

val String.toColor
    get() =
        hexToColor(this)

fun hexToColor(hex: String): Color {
    val color = hex.removePrefix("#")
    val r = color.substring(0, 2).toInt(16)
    val g = color.substring(2, 4).toInt(16)
    val b = color.substring(4, 6).toInt(16)
    return Color(r, g, b)
}


