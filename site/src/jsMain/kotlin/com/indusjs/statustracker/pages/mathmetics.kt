package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.background
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Page("/math")
@Composable
fun SubjectDetailsPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        SpanText(
            "Mathematics",
            modifier = Modifier
                .align(Alignment.Start)   // 👈 left align inside Column
                .margin(bottom = 24.px)
        )


        // Chapter 1
        ChapterSection(
            chapterTitle = "Chapter 1: Algebra",
            topics = listOf(
                Topic("1.1 Introduction to Algebra", completed = true, bookmarked = true),
                Topic("1.2 Polynomials", completed = true, bookmarked = false),
                Topic("1.3 Linear Equations", completed = false, bookmarked = false)
            )
        )

        Box(modifier = Modifier.height(24.px))

        // Chapter 2
        ChapterSection(
            chapterTitle = "Chapter 2: Geometry",
            topics = listOf(
                Topic("2.1 Basic Concepts", completed = false, bookmarked = false),
                Topic("2.2 Angles and Lines", completed = false, bookmarked = false)
            )
        )
    }
}

data class Topic(
    val name: String,
    val completed: Boolean,
    val bookmarked: Boolean
)

@Composable
fun ChapterSection(chapterTitle: String, topics: List<Topic>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.px)
    ) {
        SpanText(chapterTitle, modifier = Modifier.margin(bottom = 8.px))

        topics.forEach { topic ->
            TopicItem(topic)
            Box(modifier = Modifier.height(24.px))        }
    }
}

@Composable
fun TopicItem(topic: Topic) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .borderRadius(8.px)
            .padding(12.px)
            .background(Colors.White)
            .boxShadow(blurRadius = 6.px, color = Colors.LightGray),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Completion status circle
            Box(
                modifier = Modifier
                    .size(20.px)
                    .borderRadius(50.percent)
                    .background(if (topic.completed) Colors.Green else Colors.White)
                    .border(1.px, LineStyle.Solid, Colors.Gray) // ✅ Correct
            )
            SpanText(
                topic.name,
                modifier = Modifier.margin(left = 12.px)
            )
        }

        // Bookmark indicator
        Box(
            modifier = Modifier
                .size(16.px)
                .background(if (topic.bookmarked) Colors.Gold else Colors.Transparent)
                .borderRadius(2.px)
                .border(width = 1.px, color = Colors.Gray)

        )
    }
}
