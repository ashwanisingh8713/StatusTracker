// src/jsMain/kotlin/com/indusjs/statustracker/pages/TestEventListenerPage.kt
package com.indusjs.statustracker.pages // Corrected package name

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect // Use DisposableEffect for cleanup
import com.varabyte.kobweb.compose.css.TextAlign
import kotlinx.browser.window // Import window
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px
//import org.jetbrains.compose.web.css.TextAlign // For TextAlign.Center

@Page("/test_events") // You can access this page at http://localhost:8080/test_events
@Composable
fun TestEventListenerPage() {
    DisposableEffect(Unit) {
        println("TestEventListenerPage: Composable launched. Attaching listener...")

        // Define the listener with 'dynamic' event type
        val testListener = { event: dynamic ->
            println("RAW RESIZE TEST: Window resized! Current width: ${window.innerWidth}")
        }

        // Attach listener with standard options object
        window.addEventListener("resize", testListener, js("({ capture: false })"))
        println("TestEventListenerPage: Raw resize listener attached.")

        onDispose {
            // Detach listener
            window.addEventListener("resize", testListener, js("({ capture: false })"))
            println("TestEventListenerPage: Raw resize listener detached.")
        }
    }

    Box(Modifier.fillMaxSize().backgroundColor(Colors.LightCyan)) {
        SpanText(
            "Resize this browser window and check your browser's developer console (F12)!",
            modifier = Modifier.fillMaxSize().textAlign(TextAlign.Center).fontSize(20.px).padding(20.px)
        )
    }
}