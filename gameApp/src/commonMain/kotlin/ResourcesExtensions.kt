import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import gamebook.gameapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun t(block: Res.string.() -> StringResource): String =
    stringResource(Res.string.block())

@Composable
fun p(block: Res.drawable.() -> DrawableResource): Painter =
    painterResource(Res.drawable.block())