package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.forms.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
// Style for the page container
val VocabularyPageStyle = Modifier
    .fillMaxWidth()

@Page("/vocabulary")
@Composable
fun VocabularyPage(ctx: PageContext) {
    var searchQuery by remember { mutableStateOf("") }

    val vocabularyList = listOf(
        "Abate" to "To lessen or reduce in intensity",
        "Benevolent" to "Kind and generous",
        "Cacophony" to "A harsh, discordant mixture of sounds"
    )

    val filteredList = if (searchQuery.isBlank()) {
        vocabularyList
    } else {
        vocabularyList.filter { (word, meaning) ->
            word.contains(searchQuery, ignoreCase = true) ||
            meaning.contains(searchQuery, ignoreCase = true)
        }
    }

    Div(attrs = {
        style {
            property("background-color", "#fabcdeo")
            property("padding", "1rem")
        }
    }) {
        Column(modifier = VocabularyPageStyle) {
            H1 { Text("Vocabulary List") }

            Div(attrs = {
                style {
                    property("margin-bottom", "1rem")
                    property("width", "100%")
                }
            }) {
                Input(
                    type = InputType.Text,
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search vocabulary..."
                )
            }

            Div(attrs = {
                style {
                    property("height", "100rem")
                }
            })

            for ((word, meaning) in filteredList) {
                VocabularyItem(word = word, meaning = meaning)
            }
        }
    }
}

@Composable
fun VocabularyItem(word: String, meaning: String) {
    Column {
        H1 { Text(word) } // Word title
        P { Text(meaning) } // Meaning
        Div(attrs = {
            style {
                property("height", "0.5rem")
            }
        })
    }
}