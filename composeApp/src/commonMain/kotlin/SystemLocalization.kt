import androidx.compose.runtime.Composable
import cz.roldy.gb.MR
import dev.icerock.moko.resources.StringResource
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.Resource
import dev.icerock.moko.resources.desc.StringDesc

@Composable
fun t(block: MR.strings.() -> StringResource): String =
    StringDesc.Resource(MR.strings.block()).localized()