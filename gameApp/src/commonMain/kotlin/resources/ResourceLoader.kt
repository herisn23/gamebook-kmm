package resources

import androidx.compose.ui.text.font.FontFamily
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar

typealias Strings = Map<String, Map<String, String>>

object Resources {
    object Strings {
        val error_message_generic: String = "error.message.generic"
        val error_message_internetConnection: String = "error.message.internetConnection"
        val error_confirm: String = "error.confirm"
        val error_retry: String = "error.retry"
        val loading: String = "loading"
        val continue_: String = "continue"
        val stories: String = "stories"
    }
    object Images {
        val main_background: String = "main_background.jpg"
        val story_default: String = "story_default.jpg"
    }

    object Fonts {
        val defaultFont = "OpenSans-Medium.ttf"
        val titleFont = "Matemasie-Regular.ttf"
    }
}

expect suspend fun Resources.loadStrings(languages: List<String>): Strings

expect fun Resources.loadFile(dir: String, name: String): ByteArray?

expect fun Resources.loadFont(name: String): FontFamily

fun stringsContentToMap(content: String): Map<String, String> =
    Yaml.default.parseToYamlNode(content)
        .yamlMap.entries
        .map { (messageKey, localization) ->
            messageKey.content to localization.yamlScalar.content
        }.toMap()