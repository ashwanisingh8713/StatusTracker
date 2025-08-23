package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent

@Page("/profile")
@Composable
fun ProfilePage(ctx: PageContext) {
    Box(
        modifier = Modifier.fillMaxSize().backgroundColor(Colors.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(25.cssRem)
                .padding(2.cssRem)
                .backgroundColor(Colors.White)
                .borderRadius(1.cssRem)
                .boxShadow(blurRadius = 0.5.cssRem, color = Colors.Gray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(1.cssRem)
        ) {
            // Profile picture placeholder
            Box(
                modifier = Modifier
                    .size(6.cssRem)
                    .backgroundColor(Colors.LightBlue)
                    .borderRadius(50.percent),
                contentAlignment = Alignment.Center
            ) {
                SpanText(
                    "M",
                    modifier = Modifier.fontSize(2.cssRem)
                )
            }

            // Name
            SpanText(
                "Manoj Kumar",
                modifier = Modifier
                    .fontSize(1.5.cssRem)
                    .fontWeight(FontWeight.Bold)
            )

            // Email
            SpanText(
                "manoj@example.com",
                modifier = Modifier.color(Colors.Gray)
            )

            // Small action buttons
            Row(
                horizontalArrangement = Arrangement.spacedBy(1.cssRem)
            ) {
                Button(onClick = { println("Edit Profile") }) {
                    SpanText("Edit")
                }
                Button(onClick = { ctx.router.navigateTo("/logout") }) {
                    SpanText("Logout")
                }
            }
        }
    }
}

