package com.indusjs.statustracker.components

import com.varabyte.kobweb.compose.foundation.layout.Arrangement

// src/jsMain/kotlin/com/indusjs/statustracker/components/MarketingSection.kt

import androidx.compose.runtime.Composable
import com.indusjs.statustracker.utils.Spacer // Updated import
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Composable
fun MarketingSection() {
    Column(
        modifier = Modifier.fillMaxSize().padding(60.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SpanText(
            "Revolutionize QA with Smarter Automation",
            modifier = Modifier.fontSize(48.px).fontWeight(FontWeight.Bold).color(Colors.White).textAlign(
                TextAlign.Center).lineHeight(1.2)
        )

        Spacer(Modifier.height(40.px))

        Column(
            // FIX: Corrected alpha value - should be a Float directly, not converted to Int
            modifier = Modifier.fillMaxWidth().backgroundColor(Colors.White.copy(alpha = 1)).borderRadius(16.px).padding(30.px)
        ) {
            SpanText("“SoftQA has completely transformed our testing process. It’s reliable, efficient, and ensures our releases are always top-notch.”",
                modifier = Modifier.fontSize(20.px).color(Colors.White).fontStyle(FontStyle.Italic).lineHeight(1.5)
            )
            Spacer(Modifier.height(20.px))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Img(src = "/michael-carter.png", alt = "Michael Carter", attrs = {
                    style {
                        width(50.px)
                        height(50.px)
                        borderRadius(50.percent)
                        marginRight(15.px)
                    }
                })
                Column {
                    SpanText("Michael Carter", modifier = Modifier.color(Colors.White).fontWeight(FontWeight.SemiBold))
                    SpanText("Software Engineer at DevCore", modifier = Modifier.color(Colors.LightGray).fontSize(14.px))
                }
            }
        }

        Spacer(Modifier.height(60.px))

        SpanText("JOIN 1K TEAMS", modifier = Modifier.color(Colors.LightGray).fontSize(14.px).fontWeight(FontWeight.SemiBold).letterSpacing(1.px))

        Spacer(Modifier.height(30.px))

        // Logos (Placeholder, replace with actual images)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Img(src = "/discord-logo.svg", alt = "Discord", attrs = { style { height(30.px) } })
            Img(src = "/mailchimp-logo.svg", alt = "Mailchimp", attrs = { style { height(30.px) } })
            Img(src = "/grammarly-logo.svg", alt = "Grammarly", attrs = { style { height(30.px) } })
            Img(src = "/attentive-logo.svg", alt = "Attentive", attrs = { style { height(30.px) } })
        }
        Spacer(Modifier.height(20.px))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Img(src = "/hellosign-logo.svg", alt = "HelloSign", attrs = { style { height(30.px) } })
            Img(src = "/intercom-logo.svg", alt = "Intercom", attrs = { style { height(30.px) } })
            Img(src = "/square-logo.svg", alt = "Square", attrs = { style { height(30.px) } })
            Img(src = "/dropbox-logo.svg", alt = "Dropbox", attrs = { style { height(30.px) } })
        }
    }
}