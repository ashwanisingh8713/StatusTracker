package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb

@Page("/mynotes")
@Composable
fun MyNotesPage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.px)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Header Row (Back arrow + Title)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText("←", modifier = Modifier.fontSize(20.px).margin(right = 12.px))
                SpanText(
                    "My Notes",
                    modifier = Modifier.fontSize(22.px)
                )
            }

            Box(modifier = Modifier.height(16.px))

            // Notes list
            NoteCard(
                title = "Algebra Formulas",
                description = "Key formulas for polynomials and linear equations. Remember (a+b)^2 = a^2 + 2ab + b^2...",
                meta = "Mathematics - 2 days ago"
            )
            Box(modifier = Modifier.height(16.px))
            NoteCard(
                title = "Cell Structure",
                description = "Notes on the difference between plant and animal cells. The cell wall is a key differentiator...",
                meta = "Science - 5 days ago"
            )
            Box(modifier = Modifier.height(16.px))
            NoteCard(
                title = "Shakespearean Sonnets",
                description = "Structure of a sonnet: 14 lines, iambic pentameter, specific rhyme scheme (ABAB CDCD EFEF GG)...",
                meta = "English - 1 week ago"
            )
        }

        // Floating Add Button (bottom center)
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(56.px)
                .backgroundColor(rgb(63, 81, 181)) // Material Blue
                .borderRadius(50.percent),
            contentAlignment = Alignment.Center
        ) {
            SpanText(
                "+",
                modifier = Modifier.color(Colors.White).fontSize(28.px)
            )
        }
    }
}

@Composable
fun NoteCard(title: String, description: String, meta: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.White)
            .borderRadius(12.px)
            .boxShadow(blurRadius = 8.px, color = Colors.LightGray)
            .padding(16.px)
    ) {
        SpanText(title, modifier = Modifier.fontSize(16.px))
        Box(modifier = Modifier.height(16.px))
        SpanText(description, modifier = Modifier.color(rgb(80, 80, 80)).fontSize(14.px))
        Box(modifier = Modifier.height(16.px))
        SpanText(meta, modifier = Modifier.color(Colors.Gray).fontSize(12.px))
    }
}
