package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.attributes.InputType

@Page("/nkm")
@Composable
fun LoginPage() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SpanText("Learning Tracker", modifier = Modifier.margin(bottom = 12.px).fontSize(20.px))

        // Email field
        Input(
            type = InputType.Email,
            value = email,
            onValueChange = { newValue -> email = newValue },
            placeholder = "Email",
            modifier = Modifier
                .fillMaxWidth()
                .margin(bottom = 16.px)
        )

        // <-- Correct way to make a password field in Kobweb / Silk -->
        Input(
            type = InputType.Password,
            value = password,
            onValueChange = { newValue -> password = newValue },
            placeholder = "Password",
            modifier = Modifier
                .fillMaxWidth()
                .margin(bottom = 16.px)
        )

        Button(
            onClick = { println("Sign in: $email / $password") },
            modifier = Modifier.fillMaxWidth()
        ) {
            SpanText("Sign In")
        }

        Link(path = "/forgot-password", modifier = Modifier.margin(top = 12.px)) {
            SpanText("Forgot password?")
        }
    }
}
