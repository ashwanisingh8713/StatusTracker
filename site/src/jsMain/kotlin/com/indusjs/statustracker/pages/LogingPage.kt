package com.indusjs.statustracker.pages


import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Input
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H1

@Page
@Composable
fun AboutPage() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(20.px),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        H1 { Text("About Page - Login Form") }

        // Email Field
        Input(
            type = InputType.Email,
            value = email,
            onValueChange = { email = it },
            modifier = Modifier.width(250.px).margin(bottom = 10.px),
            placeholder = "Email address"
        )

        // Password Field
        Input(
            type = InputType.Password,
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.width(250.px).margin(bottom = 10.px),
            placeholder = "Password"
        )

        // Login Button
        Button(
            attrs = {
                onClick {
                    println("Email: $email, Password: $password")
                }
            }
        ) {
            Text("Login")
        }
    }
}
