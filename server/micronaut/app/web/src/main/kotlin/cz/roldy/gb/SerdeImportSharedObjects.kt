package cz.roldy.gb

import cz.roldy.gb.story.model.StoryMetadata
import cz.roldy.gb.story.model.YamlSource
import io.micronaut.context.annotation.Factory
import io.micronaut.serde.annotation.SerdeImport


@SerdeImport(YamlSource::class)
@SerdeImport(StoryMetadata::class)
@Factory
interface SerdeImportSharedObjects