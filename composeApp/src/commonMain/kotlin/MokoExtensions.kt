import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontFamily
import cz.roldy.gb.MR
import dev.icerock.moko.resources.FontResource
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.fontFamilyResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

@Composable
expect fun imageResource(imageResource: ImageResource): ImageBitmap

@Composable
fun mrs(block: MR.strings.() -> StringResource): String =
    StringDesc.Resource(MR.strings.block()).localized()

@Composable
fun mrp(block: MR.images.() -> ImageResource): Painter =
    painterResource(MR.images.block())

@Composable
fun mri(block: MR.images.() -> ImageResource): ImageBitmap =
    imageResource(MR.images.block())
@Composable

fun mrf(block: MR.fonts.() -> FontResource): FontFamily =
    fontFamilyResource(MR.fonts.block())
