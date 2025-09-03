package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

@Page("/WelcomBack")
@Composable
fun WelcomBack() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.px),
        verticalArrangement = Arrangement.spacedBy(20.px)
    ) {
        GreetingSection()
        ProgressCard()
    }
}

@Composable
fun GreetingSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SpanText("Welcome back,", modifier = Modifier.fontSize(16.px).color(Colors.Gray))
            SpanText("Alex Johnson", modifier = Modifier.fontSize(24.px).fontWeight(700))
        }

        // ✅ Corrected Avatar
        Box(
            modifier = Modifier
                .size(40.px)
                .background(Colors.LightGray)
                .borderRadius(50.percent),
            contentAlignment = Alignment.Center
        ) {
            SpanText("A", modifier = Modifier.fontWeight(700))
        }
    }
}

@Composable
fun ProgressCard() {
    Column(
        modifier = Modifier
            .background(Colors.RoyalBlue)
            .padding(20.px)
            .borderRadius(16.px)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        SpanText(
            "Overall Progress",
            modifier = Modifier.color(Colors.White).fontWeight(700)
        )
        SpanText(
            "You are doing great!",
            modifier = Modifier.color(Colors.White).fontSize(14.px)
        )

        // ✅ Progress bar background with rgba
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.px)
                .borderRadius(8.px)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(65.percent)
                    .height(10.px)
                    .background(Colors.White)
                    .borderRadius(8.px)
            )
        }

        SpanText(
            "65% Completed",
            modifier = Modifier.color(Colors.White).fontSize(14.px)
        )
    }

    // 🟡 Quick Actions Grid
    SpanText(
        "Quick Actions",
        modifier = Modifier.fontSize(20.px).fontWeight(700).margin(bottom = 16.px)
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.px)) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.px)) {
            ActionCard("Subjects", Colors.LightCyan)
            ActionCard("Bookmarks", Colors.LightYellow)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.px)) {
            ActionCard("Teachers", Colors.LightBlue)
            ActionCard("My Notes", Colors.LightCoral)
        }
    }
}

@Composable
fun ActionCard(title: String, bgColor: Color) {
    Box(
        modifier = Modifier
            .background(bgColor)
            .padding(20.px)
            .borderRadius(12.px)
            .width(160.px)
            .height(100.px),
        contentAlignment = Alignment.Center
    ) {
        SpanText(title, modifier = Modifier.fontWeight(600))
    }
}
