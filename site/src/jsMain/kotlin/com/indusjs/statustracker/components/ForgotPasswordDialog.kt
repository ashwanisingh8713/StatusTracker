// src/jsMain/kotlin/com/indusjs/statustracker/components/ForgotPasswordDialog.kt
// Working Forgot Password Dialog
package com.indusjs.statustracker.components

import androidx.compose.runtime.*
import com.indusjs.statustracker.utils.Spacer
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.textAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors // Make sure this import is present
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.MainScope
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.bottom
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.left
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.right
import org.jetbrains.compose.web.css.top
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.css.vw
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Composable
fun ForgotPasswordDialog(
    isVisible: Boolean,
    onDismissRequest: () -> Unit,
    onSendResetLink: (String) -> Unit
) {
    if (!isVisible) return

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    // Modal Overlay Background
    Box(
        modifier = Modifier.fillMaxSize()
            // CORRECTED: Using alpha as Int (0-255)
            .backgroundColor(Colors.Black.copy(alpha = 127)) // 127 is roughly 50% of 255
            .onClick { onDismissRequest() }
            .position(Position.Fixed)
            .left(0.px).top(0.px).right(0.px).bottom(0.px)
            .zIndex(1000)
            .justifyContent(JustifyContent.Center)
            .alignItems(AlignItems.Center)
    ) {
        // Dialog Content Box
        Column(
            modifier = Modifier
                .maxWidth(400.px)
                .width(80.percent)
                .backgroundColor(Colors.White)
                .borderRadius(8.px)
                .padding(24.px)
                // CORRECTED: Using alpha as Int (0-255) for shadow color
                .boxShadow(0.px, 4.px, 12.px, 0.px, Colors.Black.copy(alpha = (255 * 0.15).toInt())) // 15% opacity
                .onClick { it.stopPropagation() },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            SpanText("Forgot Password?",
                modifier = Modifier.fontSize(24.px).fontWeight(FontWeight.Bold).color(Colors.Black).margin(bottom = 20.px)
            )

            P(attrs = { style { fontSize(14.px); color(Colors.Gray); textAlign(TextAlign.Center) }}) {
                Text("Enter your email address below and we'll send you a link to reset your password.")
            }

            Spacer(Modifier.height(20.px))

            SpanText("Email", modifier = Modifier.align(Alignment.Start).fontSize(14.px).color(Colors.Black).margin(bottom = 8.px))
            Input(
                type = InputType.Email,
                value = email,
                onValueChange = {
                    email = it
                    emailError = ""
                    message = ""
                },
                modifier = Modifier.fillMaxWidth().padding(12.px).borderRadius(8.px).border(1.px, LineStyle.Solid, Colors.LightGray)
            )
            if (emailError.isNotEmpty()) {
                SpanText(emailError, modifier = Modifier.color(Colors.Red).fontSize(12.px).align(Alignment.Start).margin(top = 4.px))
            }

            if (message.isNotEmpty()) {
                Spacer(Modifier.height(15.px))
                SpanText(message, modifier = Modifier.color(if (message.contains("success", ignoreCase = true)) Colors.Green else Colors.Red).fontSize(14.px).textAlign(TextAlign.Center))
            }

            Spacer(Modifier.height(30.px))

            Button(
                onClick = {
                    emailError = validateEmailForForgot(email)
                    if (emailError.isEmpty()) {
                        isLoading = true
                        println("Sending reset link to: $email")
                        MainScope().launch {
                            delay(2000)
                            message = "Success! Password reset link sent to $email."
                            isLoading = false
                        }
                        onSendResetLink(email)
                    }
                },
                modifier = Modifier.fillMaxWidth().backgroundColor(Color("#007BFF")).color(Colors.White).borderRadius(8.px).padding(12.px)
                    .thenIf(isLoading) { Modifier.cursor(Cursor.NotAllowed).opacity(0.7f) }
            ) {
                if (isLoading) {
                    Text("Sending...")
                } else {
                    Text("Send Reset Link")
                }
            }

            Spacer(Modifier.height(15.px))

            Button(
                onClick = { onDismissRequest() },
                modifier = Modifier.fillMaxWidth().backgroundColor(Colors.Transparent).color(Color("#007BFF")).borderRadius(8.px).padding(12.px).border(1.px, LineStyle.Solid, Color("#007BFF"))
            ) {
                Text("Cancel")
            }
        }
    }
}

private fun validateEmailForForgot(email: String): String {
    if (email.isEmpty()) {
        return "Email cannot be empty."
    }
    val atIndex = email.indexOf('@')
    val dotIndex = email.lastIndexOf('.')

    if (atIndex == -1 || dotIndex == -1 || atIndex > dotIndex || atIndex == 0 || dotIndex == email.length - 1) {
        return "Invalid email format."
    }
    if (email.contains(" ")) {
        return "Email cannot contain spaces."
    }
    return ""
}