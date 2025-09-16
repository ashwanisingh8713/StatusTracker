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

@Page("/alex")
@Composable
fun DashboardPage() {
    val progress = 65 // percentage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.px)
    ) {
        // Top Row with Welcome and Avatar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                SpanText("Welcome back,")
                SpanText(
                    "Alex Johnson",
                    modifier = Modifier.fontSize(22.px)
                )
            }
            Box(
                modifier = Modifier
                    .size(40.px)
                    .backgroundColor(Colors.LightGray)
                    .borderRadius(50.percent),
                contentAlignment = Alignment.Center
            ) {
                SpanText("A")
            }
        }

        Spacer()
        Box(Modifier.height(20.px))


        // Progress Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .backgroundColor(Colors.RoyalBlue)
                .padding(16.px)
                .borderRadius(12.px)
        ) {
            SpanText(
                "Overall Progress",
                modifier = Modifier.color(Colors.White)
            )
            SpanText(
                "You are doing great!",
                modifier = Modifier.color(Colors.White).margin(bottom = 12.px)
            )

            // Progress Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.px)
                    .backgroundColor(Colors.LightGray)
                    .borderRadius(50.px)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress.percent) // ✅ FIX
                        .height(10.px)
                        .backgroundColor(Colors.LightBlue)
                        .borderRadius(50.px)
                )
            }

            SpanText(
                "$progress% Completed",
                modifier = Modifier
                    .color(Colors.White)
                    .align(Alignment.End)
                    .margin(top = 8.px)
            )
        }

        Spacer()
        Box(Modifier.height(20.px))

        // Quick Actions
        SpanText(
            "Quick Actions",
            modifier = Modifier.fontSize(18.px).margin(bottom = 16.px)
        )

        // Grid for Quick Actions
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionCard("📘", "Subjects", Colors.LightGreen)
                QuickActionCard("🔖", "Bookmarks", rgb(255, 223, 88)) // ✅ custom yellow
            }
            Spacer()
            Box(Modifier.height(20.px))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                QuickActionCard("👨‍🏫", "Teachers", Colors.LightBlue)
                QuickActionCard("📝", "My Notes", rgb(255, 182, 193)) // ✅ light pink
            }
        }
    }
}

@Composable
fun QuickActionCard(icon: String, title: String, bgColor: CSSColorValue) {
    Box(
        modifier = Modifier
            .width(160.px)
            .height(100.px)
            .backgroundColor(bgColor)
            .borderRadius(12.px)
            .padding(12.px),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            SpanText(icon, modifier = Modifier.fontSize(24.px).margin(bottom = 8.px))
            //SpanText(title, modifier = Modifier.fontWeight(FontWeight.Bold))
        }
    }
}
