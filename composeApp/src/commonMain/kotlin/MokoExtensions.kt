import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import cz.roldy.gb.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

@Composable
fun t(block: MR.strings.() -> StringResource): String =
    StringDesc.Resource(MR.strings.block()).localized()


@Composable
fun i(block: MR.images.() -> ImageResource): Painter =
    painterResource(MR.images.block())