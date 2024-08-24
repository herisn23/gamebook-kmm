import androidx.compose.material3.ColorScheme
import androidx.compose.ui.graphics.Color

/**
 * This class serves for printing colors from ColorsScheme to console.
 * Copy result from console and Paste it to DefaultColors
 */

private fun To00Hex(value: Int): String {
    var hex = "00" + value.toString(16)
    hex = hex.uppercase()
    return hex.substring(hex.length - 2, hex.length)
}

private val Color.h get(): String {
    val red: Int = (red * 255).toInt()
    val green: Int = (green * 255).toInt()
    val blue: Int = (blue * 255).toInt()
    val alpha: Int = (alpha * 255).toInt()

    val redHex = To00Hex(red)
    val greenHex = To00Hex(green)
    val blueHex = To00Hex(blue)
    val alphaHex = To00Hex(alpha)

    // hexBinary value: RRGGBBAA
    val str = StringBuilder("")
    str.append(redHex)
    str.append(greenHex)
    str.append(blueHex)
    str.append(alphaHex)

    return "\"#$str\".toColor"
}

fun ColorScheme.printToConsole() =
    buildString {
        append("ColorScheme(")
        append("primary=${primary.h}, ")
        append("onPrimary=${onPrimary.h}, ")
        append("primaryContainer=${primaryContainer.h}, ")
        append("onPrimaryContainer=${onPrimaryContainer.h}, ")
        append("inversePrimary=${inversePrimary.h}, ")
        append("secondary=${secondary.h}, ")
        append("onSecondary=${onSecondary.h}, ")
        append("secondaryContainer=${secondaryContainer.h}, ")
        append("onSecondaryContainer=${onSecondaryContainer.h}, ")
        append("tertiary=${tertiary.h}, ")
        append("onTertiary=${onTertiary.h}, ")
        append("tertiaryContainer=${tertiaryContainer.h}, ")
        append("onTertiaryContainer=${onTertiaryContainer.h}, ")
        append("background=${background.h}, ")
        append("onBackground=${onBackground.h}, ")
        append("surface=${surface.h}, ")
        append("onSurface=${onSurface.h}, ")
        append("surfaceVariant=${surfaceVariant.h}, ")
        append("onSurfaceVariant=${onSurfaceVariant.h}, ")
        append("surfaceTint=${surfaceTint.h}, ")
        append("inverseSurface=${inverseSurface.h}, ")
        append("inverseOnSurface=${inverseOnSurface.h}, ")
        append("error=${error.h}, ")
        append("onError=${onError.h}, ")
        append("errorContainer=${errorContainer.h}, ")
        append("onErrorContainer=${onErrorContainer.h}, ")
        append("outline=${outline.h}, ")
        append("outlineVariant=${outlineVariant.h}, ")
        append("scrim=${scrim.h}, ")
        append("surfaceBright=${surfaceBright.h}, ")
        append("surfaceDim=${surfaceDim.h}, ")
        append("surfaceContainer=${surfaceContainer.h}, ")
        append("surfaceContainerHigh=${surfaceContainerHigh.h}, ")
        append("surfaceContainerHighest=${surfaceContainerHighest.h}, ")
        append("surfaceContainerLow=${surfaceContainerLow.h}, ")
        append("surfaceContainerLowest=${surfaceContainerLowest.h}, ")
        append(")")
    }.let(::println)
