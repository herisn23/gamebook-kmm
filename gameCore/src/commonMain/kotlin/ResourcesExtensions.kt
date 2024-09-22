import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import gamebook.gamecore.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun rt(block: Res.string.() -> StringResource): String =
    stringResource(Res.string.block())

@Composable
fun rp(block: Res.drawable.() -> DrawableResource): Painter =
    painterResource(Res.drawable.block())