package engine

import androidx.compose.ui.text.intl.Locale
import cz.roldy.gb.story.model.Story

fun Story.pickLanguage() =
    pickLanguage(Locale.current.language)

fun Story.pickLanguage(selected: String) =
    when (supportedLanguages.contains(selected)) {
        true -> selected
        false -> {
            //if selected language is not supported try pick Czech
            if (supportedLanguages.contains("cs")) {
                "cs"
            } else {
                //if Czech language is not supported fallback to English
                "en"
            }
        }
    }
