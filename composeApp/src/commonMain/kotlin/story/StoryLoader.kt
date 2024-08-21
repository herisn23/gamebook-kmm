package story

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlConfiguration
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import story.model.Story
import story.model.StoryApi
import story.model.StoryLocalization
import story.model.StoryMetadata


expect suspend fun loadStoryApi(path: String, fileName:String): String
expect suspend fun loadStoryLocalization(path: String, strings:String): Map<String, String>
expect suspend fun loadStoriesApi(fileName:String): String

suspend fun loadStories(): List<StoryMetadata> =
    loadStoriesApi("stories.yaml").let {
        Yaml.default.parseToYamlNode(it).storyMetadata
    }

private val StoryMetadata.path get() = "story/$id"

suspend fun loadStory(metadata: StoryMetadata): Story = coroutineScope {
    awaitAll(
        async {
            loadStoryApi(metadata.path, "api.yaml").let {
                Yaml(
                    configuration = YamlConfiguration(
                        polymorphismStyle = PolymorphismStyle.Tag
                    )
                ).decodeFromString(StoryApi.serializer(), it)
            }
        },
        async {
            loadStoryLocalization(metadata.path, "strings").map { (lang, content) ->
                lang to Yaml.default.parseToYamlNode(content)
                    .yamlMap.entries
                    .map { (messageKey, localization) ->
                        messageKey.content to localization.yamlScalar.content
                    }.toMap()
            }.toMap().run {
                //move message key to top-level and langs underneath

                /**
                 * original:
                 * cs: {
                 *  key1: xxx
                 *  key2: xxx
                 * }
                 *
                 * switched:
                 * key1: {
                 *  cs: xxx
                 *  en: xxx
                 * }
                 */

                val messageKeys = values.map { it.keys }.flatten()
                messageKeys.associateWith { key ->
                    entries.mapNotNull { (l, m) ->
                        m[key]?.let {
                            l to it
                        }
                    }.toMap()
                }
            }
        }
    ).let { (api, localization) ->
        Story(api as StoryApi, metadata, localization as StoryLocalization)
    }
}

private val YamlNode.storyLocalization: StoryLocalization
    get() =
        yamlMap.entries.map { (messageKey, localization) ->
            messageKey.content to localization.yamlMap.entries.map { (locale, text) ->
                locale.content to text.yamlScalar.content
            }.toMap()
        }.toMap()

private val YamlNode.storyMetadata: List<StoryMetadata>
    get() =
        yamlMap.entries.map { (storyId, metadata) ->
            StoryMetadata(
                storyId.content,
                metadata.storyLocalization
            )
        }