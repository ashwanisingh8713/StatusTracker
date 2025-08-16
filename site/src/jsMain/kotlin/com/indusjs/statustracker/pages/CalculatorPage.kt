package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.px


@Page(routeOverride = "calculator")
@Composable
fun HelloPage() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SpanText(
            text = "Hello!",
           // modifier = Modifier.fontSize(csrem.2) // Make the text a bit larger
        )
    }
}