package com.ours.statustracker.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.fa.FaFacebook
import com.varabyte.kobweb.silk.components.icons.fa.FaGoogle
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.rgba

@Page("/bt-signin")
@Composable
fun BTSignInPage() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var generalError by remember { mutableStateOf<String?>(null) }
    var showPassword by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var darkMode by remember { mutableStateOf(false) }
    val breakpoint: Breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    fun validateEmail(): Boolean {
        emailError = if (email.isBlank()) {
            "Email cannot be empty."
        } else if (!email.matches(Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))) {
            "Invalid email format."
        } else {
            null
        }
        return emailError == null
    }

    fun validatePassword(): Boolean {
        passwordError = if (password.isBlank()) {
            "Password cannot be empty."
        } else if (password.length < 6) {
            "Password must be at least 6 characters."
        } else {
            null
        }
        return passwordError == null
    }

    suspend fun handleSignIn() {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        if (isEmailValid && isPasswordValid) {
            isLoading = true
            generalError = null
            try {
                delay(2000) // Simulate API call
                // Show success toast
            } catch (e: Exception) {
                generalError = "An unexpected error occurred: ${e.message}"
            } finally {
                isLoading = false
            }
        } else {
            // Ensure error messages are displayed if validation fails
            if (!isEmailValid && emailError == null) emailError = "Invalid email."
            if (!isPasswordValid && passwordError == null) passwordError = "Invalid password."
        }
    }

    val backgroundColor = if (darkMode) Color.rgb(24, 26, 27) else Colors.White
    val cardColor = if (darkMode) Color.rgb(36, 37, 38) else Colors.White
    val textColor = if (darkMode) Colors.White else Color.rgb(51, 51, 51)

    Box(
        modifier = Modifier.fillMaxSize().backgroundColor(backgroundColor).padding(16.px),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(if (breakpoint >= Breakpoint.MD) 400.px else 100.percent)
                .padding(24.px)
                .backgroundColor(cardColor)
                .borderRadius(8.px)
                .boxShadow(0.px, 0.px, 10.px, 0.px, rgba(0, 0, 0, 0.1))
                .onKeyDown { event ->
                    if (event.key == "Enter" && !isLoading) {
                        scope.launch { handleSignIn() }
                    }
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.px)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                Button(onClick = { darkMode = !darkMode }, modifier = Modifier) {
                    SpanText(if (darkMode) "☀️" else "🌙", modifier = Modifier.margin(right = 4.px))
                    SpanText(if (darkMode) "Light" else "Dark")
                }
            }
            Image(
                src = "/logo.png",
                description = "Company Logo",
                modifier = Modifier.size(80.px).margin(bottom = 16.px)
            )
            SpanText(
                "Sign In",
                modifier = Modifier.fontSize(28.px).fontWeight(FontWeight.Bold).textAlign(TextAlign.Center).color(textColor)
            )

            if (generalError != null) {
                SpanText(
                    generalError!!,
                    modifier = Modifier.color(Colors.Red).margin(bottom = 8.px).textAlign(TextAlign.Center)
                )
            }

            Input(
                type = InputType.Email,
                value = email,
                onValueChange = {
                    email = it
                    if (emailError != null) validateEmail()
                },
                placeholder = "Email",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.px)
                    .borderRadius(4.px)
                    .border(width = 1.px, style = LineStyle.Solid, color = if (emailError != null) Colors.Red else Colors.LightGray)
                    .padding(left = 12.px, right = 12.px)
                    .ariaInvalid(emailError != null)
                    .color(textColor)
            )

            if (emailError != null) {
                SpanText(emailError!!, modifier = Modifier.color(Colors.Red).fontSize(12.px).margin(bottom = 4.px))
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Input(
                    type = if (showPassword) InputType.Text else InputType.Password,
                    value = password,
                    onValueChange = {
                        password = it
                        if (passwordError != null) validatePassword()
                    },
                    placeholder = "Password",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.px)
                        .borderRadius(4.px)
                        .border(width = 1.px, style = LineStyle.Solid, color = if (passwordError != null) Colors.Red else Colors.LightGray)
                        .padding(left = 12.px, right = 40.px)
                        .ariaInvalid(passwordError != null)
                        .color(textColor)
                )
                // Use a simple text button for password toggle
                Button(
                    onClick = { showPassword = !showPassword },
                    modifier = Modifier
                        .margin(left = (-36).px)
                        .align(Alignment.CenterEnd)
                        .color(Colors.Gray)
                        .fontSize(16.px)
                        .ariaLabel(if (showPassword) "Hide password" else "Show password")
                ) {
                    SpanText(if (showPassword) "🙈" else "👁️")
                }
            }

            if (passwordError != null) {
                SpanText(passwordError!!, modifier = Modifier.color(Colors.Red).fontSize(12.px).margin(bottom = 4.px))
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = rememberMe, onCheckedChange = { rememberMe = it }, modifier = Modifier.ariaLabel("Remember Me"))
                SpanText("Remember Me", modifier = Modifier.margin(left = 8.px))
                SpanText(
                    "Forgot Password?",
                    modifier = Modifier
                        .color(Colors.Blue)
                        .fontSize(14.px)
                        .cursor(Cursor.Pointer)
                        .onClick { /* TODO: Navigate to forgot password page */ }
                        .ariaLabel("Forgot Password")
                        .tabIndex(0)
                        .onKeyDown { event -> if (event.key == "Enter" || event.key == " ") {/* TODO: Navigate */} }
                )
            }

            if (isLoading) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    // Simple spinner
                    SpanText("⏳ Signing in...", modifier = Modifier.color(Colors.Blue).fontSize(16.px))
                }
            }

            Button(
                onClick = {
                    generalError = null
                    scope.launch { handleSignIn() }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth().height(48.px)
            ) {
                if (isLoading) {
                    SpanText("Loading...", modifier = Modifier.color(Colors.White))
                } else {
                    SpanText("Sign In")
                }
            }
            Row(modifier = Modifier.fillMaxWidth().margin(top = 8.px, bottom = 8.px), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = { /* TODO: Google sign-in */ },
                    modifier = Modifier.margin(right = 8.px),
                    enabled = !isLoading
                ) {
                    FaGoogle()
                    SpanText("Google", modifier = Modifier.margin(left = 8.px))
                }
                Button(
                    onClick = { /* TODO: Facebook sign-in */ },
                    enabled = !isLoading
                ) {
                    FaFacebook()
                    SpanText("Facebook", modifier = Modifier.margin(left = 8.px))
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth().margin(top = 16.px),
                horizontalArrangement = Arrangement.Center
            ) {
                SpanText("Don't have an account? ")
                SpanText(
                    "Sign Up",
                    modifier = Modifier
                        .color(Colors.Blue)
                        .fontWeight(FontWeight.Bold)
                        .cursor(Cursor.Pointer)
                        .onClick { /* TODO: Navigate to sign-up page */ }
                        .ariaLabel("Sign Up")
                        .tabIndex(0)
                        .onKeyDown { event -> if (event.key == "Enter" || event.key == " ") {/* TODO: Navigate */} }
                )
            }
        }
    }
}
