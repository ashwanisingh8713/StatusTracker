package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.*
import com.varabyte.kobweb.silk.components.icons.fa.FaBookOpen
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*

@Page(routeOverride = "/frontpage")
@Composable
fun FrontPageContent() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize().background(Colors.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .background(Colors.White)
                .padding(32.px)
                .maxWidth(400.px)
                .borderRadius(12.px),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.px)
        ) {
            // Logo
            FaBookOpen(
                modifier = Modifier.size(64.px).color(Colors.Blue)
            )

            // Title
            SpanText(
                "Learning Tracker",
                modifier = Modifier.fontSize(28.px).fontWeight(700)
            )

            // Subtitle
            SpanText(
                "Sign in to continue your journey",
                modifier = Modifier.fontSize(16.px).color(Colors.Gray)
            )

            // Email input
            Column(modifier = Modifier.fillMaxWidth().maxWidth(300.px)) {
                SpanText("Email", modifier = Modifier.fontWeight(600).margin(bottom = 4.px))
                TextInput(
                    text = email,
                    onTextChange = { newEmail -> email = newEmail },
                    placeholder = "alex.j@example.com",
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Password input
            Column(modifier = Modifier.fillMaxWidth().maxWidth(300.px)) {
                SpanText("Password", modifier = Modifier.fontWeight(600).margin(bottom = 4.px))

                TextInput(
                    text = password,
                    onTextChange = { password -> email = password }, // ✅ correct parameter
                    placeholder = "Enter your password",
                    modifier = Modifier.fillMaxWidth().maxWidth(300.px)
                )
            }

            // Sign In button
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        message = "Please fill in all fields"
                    } else {
                        message = "Signing in with $email"
                        // TODO: Implement actual authentication
                    }
                },
                modifier = Modifier.fillMaxWidth().maxWidth(300.px).height(45.px),
            ) {
                SpanText("Sign In")
            }

            // Forgot password link
            Link(
                path = "/forgot-password",
                text = "Forgot password?",
                modifier = Modifier.color(Colors.Blue).fontSize(14.px)
            )

            // Show validation or login message
            if (message.isNotEmpty()) {
                SpanText(message, modifier = Modifier.margin(top = 8.px).color(Colors.Red))
            }
        }
    }
}


