package cz.roldy.gb.story.model

typealias I18NSource = Map<String, Map<String, String>>

interface MessageSource {
    val i18n: I18NSource
}

operator fun MessageSource.get(
    locale: String,
    key: String,
    defaultText: String = "__${locale}__${key}__"
) =
    i18n[locale]?.get(key) ?: defaultText