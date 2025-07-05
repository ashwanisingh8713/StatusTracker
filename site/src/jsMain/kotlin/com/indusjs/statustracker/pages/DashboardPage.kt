package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.indusjs.data.model.LearningEntry
import com.indusjs.statustracker.AppStyles
import com.indusjs.statustracker.utils.Redirection
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.div
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.times
import org.w3c.dom.Window


@Page(Redirection.DASHBOARD)
@Composable
fun LearningListPage(ctx: PageContext) {
    // Sample data (in a real app, this would come from a backend or state management)
    val learningEntries = remember {
        mutableStateListOf(
            LearningEntry(
                id = "1",
                subject = "Mathematics",
                chapter = "Algebra",
                topic = "Linear Equations",
                startTime = "10:10",
                endTime = "12:10",
                description = "Understanding the basics of solving linear equations with one variable.",
                status = "In Progress",
                duration = "2 Hrs"
            ),
            LearningEntry(
                id = "2",
                subject = "Science",
                chapter = "Physics",
                topic = "Newton's Laws",
                startTime = "13:00",
                endTime = "14:30",
                description = "Detailed study of Newton's three laws of motion and their applications.",
                status = "Pending",
                duration = "3 Hrs"
            ),
            LearningEntry(
                id = "3",
                subject = "History",
                chapter = "World War II",
                topic = "Causes of WWII",
                startTime = "15:15",
                endTime = "18:30",
                description = "Examining the political, economic, and social factors leading to World War II.",
                status = "Completed",
                duration = "3 Hrs 15 Mins"
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(AppStyles.BackgroundColor)
            .padding(left = AppStyles.MarginDefault, right = AppStyles.MarginDefault),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        SpanText("My Learning Progress",
            modifier = Modifier
                .fontSize(AppStyles.FontSizeLarge)
                .color(Color("Green"))
                //.margin(bottom = AppStyles.PaddingDefault * 2)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth() // Constrain width for better readability on large screens
                .maxWidth(1200.px) // Max width for content
        ) {
            if (learningEntries.isEmpty()) {
                SpanText("No learning entries yet!", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor))
            } else {
                learningEntries.forEach { entry ->
                    LearningEntryRow(entry)
                }
            }
        }
    }
}

@Composable
fun LearningEntryRow(entry: LearningEntry, modifier: Modifier = Modifier) {
    val currentBreakpoint = rememberBreakpoint()
    val isMobile = currentBreakpoint <= Breakpoint.MD

    // Conditionally render based on window width
    if (isMobile) {
        LearningEntryRowMobile(entry, modifier)
    } else {
        LearningEntryRowDesktop(entry, modifier)
    }
}

// Mobile version of the LearningEntryRow
@Composable
fun LearningEntryRowMobile(entry: LearningEntry, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppStyles.PaddingDefault)
            .margin(bottom = AppStyles.MarginDefault)
            .borderRadius(AppStyles.BorderRadiusDefault)
            .backgroundColor(Colors.White)
            .border(1.px, LineStyle.Groove),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(AppStyles.MarginDefault / 2) // Small spacing between stacked items
    ) {
        // Main details (Subject, Chapter, Topic)
        Column {
            SpanText("Subject: ${entry.subject}", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor).fontWeight(com.varabyte.kobweb.compose.css.FontWeight.Bold))
            SpanText("Chapter: ${entry.chapter}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
            SpanText("Topic: ${entry.topic}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
        }

        // Time and Duration on one row for better compactness
        Row(
            modifier = Modifier.fillMaxWidth().margin(top = AppStyles.MarginDefault / 2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                SpanText("Start: ${entry.startTime}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
                SpanText("End: ${entry.endTime}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
            }
            SpanText("Duration: ${entry.duration}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.SecondaryColor).fontWeight(com.varabyte.kobweb.compose.css.FontWeight.Bold))
        }

        // Status and Description
        val statusColor = when (entry.status) {
            "Completed" -> AppStyles.SuccessColor
            "Pending" -> AppStyles.PendingColor
            "In Progress" -> AppStyles.CompletedColor
            else -> AppStyles.TextColor
        }
        SpanText("Status: ${entry.status}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(statusColor).fontWeight(com.varabyte.kobweb.compose.css.FontWeight.Bold).margin(top = AppStyles.MarginDefault / 2))
        SpanText("Description: ${entry.description}",
            modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor).margin(top = AppStyles.MarginDefault / 4))
    }
}
@Composable
fun LearningEntryRowDesktop(entry: LearningEntry, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(left=AppStyles.MarginDefault)
            .margin(bottom = AppStyles.MarginDefault)
            .borderRadius(AppStyles.BorderRadiusDefault)
            .backgroundColor(Colors.White)
            .border(1.px, LineStyle.Groove),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left section: Subject, Chapter, Topic
        Column(modifier = Modifier
            .flexGrow(1)
            .padding(right = AppStyles.MarginDefault)) {
            SpanText("Subject: ${entry.subject}", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor))
            SpanText("Chapter: ${entry.chapter}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
            SpanText("Topic: ${entry.topic}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
        }

        // Middle section: Time and Duration
        Column(modifier = Modifier
            .flexGrow(1)
            .padding(right = AppStyles.MarginDefault)) {
            SpanText("Start: ${entry.startTime}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
            SpanText("End: ${entry.endTime}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
            SpanText("Duration: ${entry.duration}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
        }

        // Right section: Status and Description (can be a summary or full text)
        Column(modifier = Modifier.flexGrow(1f)) {
            val statusColor = when (entry.status) {
                "Completed" -> AppStyles.SuccessColor
                "Pending" -> AppStyles.PendingColor
                "In Progress" -> AppStyles.CompletedColor // Using a different blue for in progress
                else -> AppStyles.TextColor
            }
            SpanText("Status: ${entry.status}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(statusColor))
            SpanText("Description: ${entry.description.take(50)}...", // Truncate for brevity in list
                modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
        }
    }
}


