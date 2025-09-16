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
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb

@Page("/david")
@Composable
fun TeacherProfilePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.px)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Back arrow row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SpanText("←", modifier = Modifier.fontSize(20.px))
            }

            Box(Modifier.height(20.px))

            // Profile Circle with initial
            Box(
                modifier = Modifier
                    .size(100.px)
                    .backgroundColor(rgb(100, 149, 237)) // light blue
                    .borderRadius(50.percent),
                contentAlignment = Alignment.Center
            ) {
                SpanText(
                    "D",
                    modifier = Modifier.color(Colors.White).fontSize(36.px)
                )
            }

            Box(Modifier.height(16.px))

            // Name + Title
            SpanText(
                "Mr. David",
                modifier = Modifier.fontSize(22.px)
            )
            SpanText(
                "Mathematics Teacher",
                modifier = Modifier.fontSize(16.px).color(Colors.Gray)
            )

            Box(Modifier.height(24.px))

            // Details Card
            InfoCard(
                title = "Details",
                items = listOf(
                    "✉ mr.david@example.com",
                    "💼 12 Years of Experience",
                    "🎓 M.Sc. in Mathematics"
                )
            )

            Box(Modifier.height(16.px))

            // Subjects Card
            InfoCard(
                title = "Subjects Taught",
                items = listOf(
                    "Mathematics (Grade 10)",
                    "Advanced Algebra (Grade 11)"
                )
            )
        }
    }
}

@Composable
fun InfoCard(title: String, items: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .backgroundColor(Colors.White)
            .borderRadius(12.px)
            .boxShadow(blurRadius = 8.px, color = Colors.LightGray)
            .padding(16.px)
    ) {
        SpanText(title, modifier = Modifier.fontSize(16.px))
        Box(Modifier.height(12.px))
        items.forEach { item ->
            SpanText(item, modifier = Modifier.fontSize(14.px).color(Colors.DarkGray))
            Box(Modifier.height(8.px))
        }
    }
}
