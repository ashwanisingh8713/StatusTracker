package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb

@Page("/teachers")
@Composable
fun TeachersPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.px)
    ) {
        // Header Row (Back arrow + Title)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText("←", modifier = Modifier.fontSize(20.px).margin(right = 12.px))
            SpanText(
                "Teachers",
                modifier = Modifier.fontSize(22.px)
            )
        }

        // <-- Use Box for spacing (do not rely on Spacer(modifier=...)) -->
        Box(Modifier.height(20.px))

        // Teacher List (cards)
        TeacherCard("D", "Mr. David", "Mathematics", Colors.LightBlue)
        Box(Modifier.height(16.px))
        TeacherCard("C", "Ms. Curie", "Science", Colors.LightGreen)
        Box(Modifier.height(16.px))
        TeacherCard("J", "Mr. Jones", "History", rgb(255, 182, 193)) // light pink
    }
}

@Composable
fun TeacherCard(initial: String, name: String, subject: String, bgColor: CSSColorValue) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.White)
            .borderRadius(12.px)
            .boxShadow(blurRadius = 8.px, color = Colors.LightGray)
            .padding(16.px)
            .margin(bottom = 8.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Circle avatar with initial
        Box(
            modifier = Modifier
                .size(48.px)
                .backgroundColor(bgColor)
                .borderRadius(50.percent),
            contentAlignment = Alignment.Center
        ) {
            SpanText(
                initial,
                modifier = Modifier.fontSize(18.px)
            )
        }

        Column(modifier = Modifier.margin(left = 16.px)) {
            SpanText(name, modifier = Modifier.fontSize(16.px))
            SpanText(subject, modifier = Modifier.color(Colors.Gray).fontSize(14.px))
        }
    }
}
