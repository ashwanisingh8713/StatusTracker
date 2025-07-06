package com.indusjs.statustracker.components

import androidx.compose.runtime.Composable
import androidx.compose.web.events.SyntheticMouseEvent
import com.indusjs.statustracker.AppStyles
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color.Companion.rgba
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.left
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.times

/**
 * A reusable message alert dialog with "Ok" and "Cancel" buttons.
 *
 * @param message The message to display in the dialog.
 * @param onOk Callback for when the "Ok" button is clicked.
 * @param onCancel Callback for when the "Cancel" button is clicked.
 */
@Composable
fun MessageAlertDialog(
    message: String,
    onOk: () -> Unit,
    onCancel: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(rgba(0, 0, 0, 0.5f)) // Semi-transparent overlay
            .position(Position.Fixed) // Fixed position to cover the screen
            .zIndex(1000) // Ensure it's on top of other content
            .top(0.px).left(0.px), // Cover the whole screen
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .maxWidth(400.px) // Max width for the dialog content
                .padding(AppStyles.PaddingDefault * 2)
                .borderRadius(AppStyles.BorderRadiusDefault)
                .backgroundColor(Colors.White)
                .border(1.px, LineStyle.Solid)
                .boxShadow(offsetX = 10.px, offsetY = 10.px, blurRadius = 20.px, spreadRadius = 0.px, color = rgba(0, 0, 0, 0.2f)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AppStyles.PaddingDefault)
        ) {
            SpanText(
                message,
                modifier = Modifier
                    .fontSize(AppStyles.FontSizeMedium)
                    .color(AppStyles.COLOR_LABEL_TEXT)
                    .textAlign(com.varabyte.kobweb.compose.css.TextAlign.Left)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onOk as (SyntheticMouseEvent) -> Unit,
                    modifier = Modifier
                        .backgroundColor(AppStyles.PrimaryColor)
                        .color(Colors.White)
                        .padding(AppStyles.MarginDefault, AppStyles.PaddingDefault)
                        .borderRadius(AppStyles.BorderRadiusDefault)
                        .border(0.px)
                        .cursor(com.varabyte.kobweb.compose.css.Cursor.Pointer)
                        .fontSize(AppStyles.FontSizeNormal)
                ) {
                    SpanText("Ok")
                }

                com.indusjs.statustracker.utils.Spacer(modifier = Modifier.width(50.px))

                Button(
                    onClick = onCancel as (SyntheticMouseEvent) -> Unit,
                    modifier = Modifier
                        .backgroundColor(AppStyles.PrimaryColor)
                        .color(Colors.White)
                        .padding(AppStyles.MarginDefault, AppStyles.PaddingDefault)
                        .borderRadius(AppStyles.BorderRadiusDefault)
                        .border(0.px)
                        .cursor(com.varabyte.kobweb.compose.css.Cursor.Pointer)
                        .fontSize(AppStyles.FontSizeNormal)
                ) {
                    SpanText("Cancel")
                }
            }
        }
    }
}