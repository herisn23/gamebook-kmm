package core.story

import core.async.mapAsync
import com.charleskorn.kaml.Yaml
import com.charleskorn.kaml.YamlList
import com.charleskorn.kaml.YamlNode
import com.charleskorn.kaml.yamlMap
import com.charleskorn.kaml.yamlScalar
import cz.roldy.gb.story.CharactersApiPart
import cz.roldy.gb.story.DefaultsApiPart
import cz.roldy.gb.story.SectionsApiPart
import cz.roldy.gb.story.StoryApiPart
import cz.roldy.gb.story.model.Story
import cz.roldy.gb.story.model.StoryDefinition
import cz.roldy.gb.story.model.StoryLocalization
import cz.roldy.gb.story.model.StoryMetadata
import cz.roldy.gb.story.model.api.StoryCharacters
import cz.roldy.gb.story.model.api.StoryDefaults
import cz.roldy.gb.story.model.api.StorySections
import core.http.sac
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

suspend fun loadStories(): List<StoryMetadata> =
    sac.stories().let {
        Yaml.default.parseToYamlNode(it).storyMetadata
    }

suspend fun loadStory(metadata: StoryMetadata): Story = coroutineScope {
    awaitAll(
        async {
            awaitAll(
                DefaultsApiPart.fetch(this, metadata),
                SectionsApiPart.fetch(this, metadata),
                CharactersApiPart.fetch(this, metadata)
            ).let { (defaults, sections, characters) ->
                StoryDefinition(
                    defaults as StoryDefaults,
                    (sections as StorySections).sections,
                    (characters as StoryCharacters).characters,
                )
            }
        },
        async {
            metadata.supportedLanguages.mapAsync { lang ->
                lang to sac.strings(metadata.id, lang)
            }.associate { (lang, content) ->
                lang to Yaml.default.parseToYamlNode(content)
                    .yamlMap.entries
                    .map { (messageKey, localization) ->
                        messageKey.content to localization.yamlScalar.content
                    }.toMap()

            }
        }
    ).let { (definition, localization) ->
        Story(definition as StoryDefinition, metadata, localization as StoryLocalization)
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
            val supportedLanguages = metadata.yamlMap.get<YamlList>("supportedLanguages")
            val localization = metadata.yamlMap.get<YamlNode>("baseTexts")
            StoryMetadata(
                storyId.content,
                supportedLanguages!!.items.map { it.yamlScalar.content },
                localization!!.storyLocalization
            )
        }

suspend fun <T> StoryApiPart<T>.fetch(scope: CoroutineScope, metadata: StoryMetadata): Deferred<T> =
    scope.async {
        Yaml.default.decodeFromString(serializer, sac.api(metadata.id, path()))
    }