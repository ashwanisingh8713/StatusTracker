package com.ours.statustracker.components

import androidx.compose.runtime.Composable
import com.ours.statustracker.AppStyles
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.zIndex
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.bottom
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.right
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div

@Composable
fun Toast(message: String, isMobile:Boolean, visible: Boolean, onClose: () -> Unit) {
    if (visible) {
        Div(attrs = {
            style {
                position(Position.Fixed)
                bottom(16.px) // Adjust position as needed
                left(if(isMobile) 10.percent else 32.percent)
                right(if(isMobile) 10.percent else 35.percent)
                backgroundColor(value= AppStyles.COLOR_TOAST_BACKGROUND)
                padding(8.px)
                borderRadius(4.px)
                textAlign("center")
                zIndex(1000) // Ensure it appears on top
                // Add fade-in/fade-out animation with CSS
                // or use JavaScript for more control
            }
        }) {
            Row() {
                SpanText(
                    message, modifier = Modifier.textAlign(textAlign = TextAlign.Left)
                        .color(
                            AppStyles.COLOR_LABEL_TEXT
                        )
                )

                SpanText("x", modifier = Modifier.width(45.px).height(35.px).margin(left = 20.px).fontSize(25.px).textAlign(
                    TextAlign.Center).color(AppStyles.COLOR_LABEL_TEXT)
                    .padding(bottom = 40.px)
                    .border(2.px, style = LineStyle.Solid, AppStyles.COLOR_LABEL_TEXT)
                    .onClick { onClose() }.cursor(Cursor.Pointer)
                )
                // Optional: Add a close button
                /*Span(attrs = {
                    style {
                        cursor(Cursor.Pointer)
                        marginLeft(8.px)
                    }
                    onClick { onClose() }
                }) {

                }*/
            }
        }



    }
}

