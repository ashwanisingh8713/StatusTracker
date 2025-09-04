package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.borderStyle
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Text




@Page(routeOverride = "/welcom_to_bharat_page")
@Composable
fun WelcomeToBharatPage() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // State to track if user signed in
    var isSignedIn by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(Colors.White),
        contentAlignment = Alignment.Center
    ) {
        if (isSignedIn) {
            // Welcome screen
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SpanText(
                    "Welcome To Ashwani kumar singh !",
                    modifier = Modifier.fontSize(24.px).color(Colors.Green)

                        .textAlign(TextAlign.Center)
                        .margin(top = 16.px)

                )
            }
        } else {
            // Sign-in form
            Column(
                modifier = Modifier
                    .width(400.px)
                    .padding(24.px)
                    .borderRadius(12.px)
                    .boxShadow(blurRadius = 8.px, color = Colors.Gray),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Logo & Title
                SpanText(
                    "BIT",
                    modifier = Modifier.fontWeight(700).fontSize(22.px).textAlign(TextAlign.Center)
                )
                SpanText(
                    "Welcome To! Vijay Info Technologies",
                    modifier = Modifier.fontSize(20.px).fontWeight(600)
                        .textAlign(TextAlign.Center)
                        .margin(top = 16.px)
                )
                SpanText(
                    "Sign in to access and enter your daily work log",
                    modifier = Modifier.color(Colors.Gray).fontSize(14.px)
                        .textAlign(TextAlign.Center)
                        .margin(top = 16.px)
                )

                // Spacer
                Box(modifier = Modifier.height(16.px))

                // Email Input
                TextInput(
                    text = email,
                    onTextChange = { email = it },
                    placeholder = "Enter your email",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.px)
                )

                // Password Input
                TextInput(
                    text = password,
                    onTextChange = { password = it },
                    placeholder = "Enter your password",
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.px)
                )

                // Sign-in Button
                Button(attrs = {
                    onClick { isSignedIn = true }  // click event
                    style {
                        width(200.px)
                        height(40.px)
                        backgroundColor(rgb(0, 0, 255)) // blue color
                        color(rgb(255, 255, 255))       // white text
                        borderRadius(8.px)
                        cursor("pointer")
                        borderStyle(LineStyle.None)     // correct type
                        borderWidth(0.px)
                    }
                }) {
                    Text("Sign in")
                }

                // Spacer
                Box(modifier = Modifier.height(10.px))

                // OR text
                SpanText(
                    "O/R",
                    modifier = Modifier.fontSize(12.px).color(Colors.Gray)
                )
            }
        }
    }
}




