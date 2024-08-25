package resources

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
}

expect suspend fun Resources.loadStrings(languages: List<String>): Strings

fun stringsContentToMap(content: String): Map<String, String> =
    Yaml.default.parseToYamlNode(content)
        .yamlMap.entries
        .map { (messageKey, localization) ->
            messageKey.content to localization.yamlScalar.content
        }.toMap()