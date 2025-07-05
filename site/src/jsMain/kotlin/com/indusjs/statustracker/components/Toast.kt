package com.indusjs.statustracker.components

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.css.margin
import com.varabyte.kobweb.compose.css.zIndex
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.bottom
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.right
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span

@Composable
fun Toast(message: String, visible: Boolean, onClose: () -> Unit) {
    if (visible) {
        Div(attrs = {
            style {
                position(Position.Fixed)
                bottom(16.px) // Adjust position as needed
                left(0.px)
                right(0.px)
                margin(left = 10.px, right = 10.px)
                backgroundColor(Color("#333333"))
                color(Color("#333333"))
                padding(8.px)
                borderRadius(4.px)
                textAlign("center")
                zIndex(1000) // Ensure it appears on top
                // Add fade-in/fade-out animation with CSS
                // or use JavaScript for more control
            }
        }) {
            Text(message)
            // Optional: Add a close button
            Span(attrs = {
                style {
                    cursor(Cursor.Pointer)
                    marginLeft(8.px)
                }
                onClick { onClose() }
            }) {
                Text("x")
            }
        }
    }
}