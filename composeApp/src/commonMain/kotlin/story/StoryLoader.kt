package story

import async.mapAsync
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.StoryApi
import cz.roldy.gb.story.model.StoryLocalization
import cz.roldy.gb.story.model.StoryMetadata
import http.sac
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun loadStories(): List<StoryMetadata> =
    sac.stories().source.let {
        Yaml.default.parseToYamlNode(it).storyMetadata
    }

suspend fun loadStory(metadata: StoryMetadata): Story = coroutineScope {
    awaitAll(
        async {
            Yaml().decodeFromString(StoryApi.serializer(), sac.api(metadata.id).source)
        },
        async {
            sac.localizations(metadata.id).mapAsync { lang ->
                lang to sac.localizations(metadata.id, lang).source
            }.associate { (lang, content) ->
                lang to Yaml.default.parseToYamlNode(content)
                    .yamlMap.entries
                    .map { (messageKey, localization) ->
                        messageKey.content to localization.yamlScalar.content
                    }.toMap()

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