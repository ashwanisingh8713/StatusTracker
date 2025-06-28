// src/jsMain/kotlin/com/indusjs/statustracker/utils/WindowUtils.kt
package com.indusjs.statustracker.utils

import androidx.compose.runtime.*
import kotlinx.browser.window
// REMOVED: import org.w3c.dom.Event // This import is causing the "not found" error

/**
 * A Composable that remembers and provides the current window's inner width,
 * updating its value automatically on window resize events.
 */
@Composable
fun rememberWindowWidth(): State<Int> {
    val width = remember { mutableStateOf(window.innerWidth) }

    // --- DEBUGGING PRINTS (kept for now, you can remove them later) ---
    println("rememberWindowWidth: Composable entered. Initial width: ${width.value}")
    // --- END DEBUGGING PRINTS ---

    DisposableEffect(Unit) {
        // Changed: Removed explicit type annotation ': (Event) -> Unit'.
        // The compiler should infer the type of 'event' from 'window.addEventListener'.
        val listener = { event: dynamic ->
            val newWidth = window.innerWidth
            if (width.value != newWidth) { // Only update if width actually changed
                width.value = newWidth
                // --- DEBUGGING PRINTS ---
                println("Window resized! New width: ${width.value}")
                // --- END DEBUGGING PRINTS ---
            }
        }

        window.addEventListener("resize", listener, js("({ capture: false })"))

        // --- DEBUGGING PRINTS ---
        println("rememberWindowWidth: Resize listener attached.")
        // --- END DEBUGGING PRINTS ---

        onDispose {
            window.addEventListener("resize", listener, js("({ capture: false })"))

            // --- DEBUGGING PRINTS ---
            println("rememberWindowWidth: Resize listener detached.")
            // --- END DEBUGGING PRINTS ---
        }
    }
    return width
}

/**
 * A simple Spacer composable for adding empty space.
 */
@Composable
fun Spacer(modifier: com.varabyte.kobweb.compose.ui.Modifier) {
    com.varabyte.kobweb.compose.foundation.layout.Box(modifier = modifier)
}