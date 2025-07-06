// src/jsMain/kotlin/com/indusjs/statustracker/components/LoginSection.kt
package com.indusjs.statustracker.components

import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.utils.Spacer
import com.indusjs.statustracker.utils.ValidationUtil.Companion.validateEmail
import com.indusjs.statustracker.utils.ValidationUtil.Companion.validatePassword
import com.indusjs.statustracker.viewmodel.SignInViewModule
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.textAlign
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.alignSelf
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginLeft
import org.jetbrains.compose.web.css.marginRight
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text
// CORRECTED IMPORTS:
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.compose.getKoin
// IMPORTANT IMPORTS FOR STATE DELEGATION
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.Composable
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.utils.ValidationUtil


@Composable
fun LoginSection(ctx: PageContext, onForgotPasswordClick: () -> Unit, showToast: (Boolean, String) -> Unit) {
    // Getting SignInViewModel
    val signInViewModel: SignInViewModule = getKoin().get<SignInViewModule>()
    var showLoader by remember { mutableStateOf(false) }

    signInViewModel.getCoroutineScope.launch {
        signInViewModel.loginState.collectLatest {
            when(it.signInResponse) {
                is ResourceUiState.Success -> {
                    showLoader = false
                    ctx.router.navigateTo(Redirection.DAILY_WORK_LOG) // Navigate to a protected page
                    println("Login Success")
                }
                is ResourceUiState.Error -> {
                    showLoader = false
                    showToast(true, "Error! ${it.signInResponse.message}")
                    println("Login Error")
                }
                is ResourceUiState.Idle -> {
                    println("Login Idle")
                }
                is ResourceUiState.Empty -> {
                    println("Login Empty")
                }
                is ResourceUiState.Loading -> {
                    showLoader = true
                    println("Login Loading")
                }
            }
        }
    }

    val onSignInClick: (email: String, password: String) -> Unit = { email, password ->
        signInViewModel.signInRequest(email, password)
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val currentBreakpoint = rememberBreakpoint() // NEW: Get the current breakpoint here

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .maxWidth(500.px)
            // FIX: Conditional padding based on breakpoint, replacing .at and .self
            .padding(
                topBottom = if (currentBreakpoint < Breakpoint.MD) 20.px else 40.px,
                leftRight = if (currentBreakpoint < Breakpoint.MD) 20.px else 40.px
            ),
        verticalArrangement = Arrangement.Center
    ) {
        // SoftQA Logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Img(src = "/favicon.ico", alt = "SoftQA Logo", attrs = {
                style {
                    width(40.px)
                    height(40.px)
                    marginRight(10.px)
                }
            })
            SpanText("BIT", modifier = Modifier.fontSize(24.px).fontWeight(FontWeight.Bold).color(
                Colors.Black))
        }
        SpanText(
            "Welcome To! Bharat Info Technologies",
            modifier = Modifier.fontSize(32.px).fontWeight(FontWeight.Bold).color(Colors.Black).margin(top = 40.px, bottom = 10.px)
        )
        P(attrs = { style { fontSize(20.px); color(Colors.Gray); textAlign(TextAlign.Center) }}) {
            Text("Sign in to access and enter your daily work log.")
        }

        Spacer(Modifier.height(40.px))

        // Email Input
        SpanText("Email", modifier = Modifier.align(Alignment.Start).fontSize(14.px).color(Colors.Black).margin(bottom = 8.px))
        Input(
            type = InputType.Email,
            value = email,
            onValueChange = {
                email = it
                emailError = ""
            },
            modifier = Modifier.fillMaxWidth().padding(12.px).borderRadius(8.px).border(1.px,
                LineStyle.Solid, Colors.LightGray
            )
        )
        if (emailError.isNotEmpty()) {
            SpanText(emailError, modifier = Modifier.color(Colors.Red).fontSize(12.px).align(Alignment.Start).margin(top = 4.px))
        }

        Spacer(Modifier.height(20.px))

        // Password Input
        SpanText("Password", modifier = Modifier.align(Alignment.Start).fontSize(14.px).color(Colors.Black).margin (bottom = 8.px))
        Input(
            type = InputType.Password,
            value = password,
            onValueChange = {
                password = it
                passwordError = ""
            },
            modifier = Modifier.fillMaxWidth().padding(12.px).borderRadius(8.px).border(1.px,
                LineStyle.Solid, Colors.LightGray
            )
        )
        if (passwordError.isNotEmpty()) {
            SpanText(passwordError, modifier = Modifier.color(Colors.Red).fontSize(12.px).align(Alignment.Start).margin (top = 4.px))
        }

        A(href = "#", attrs = {
            style {
                alignSelf(org.jetbrains.compose.web.css.AlignSelf.End)
                fontSize(14.px)
                color(Color("#007BFF"))
                marginTop(10.px)
            }
            onClick {
                it.preventDefault() // Prevents the browser from navigating away
                onForgotPasswordClick() // Call the lambda to show the dialog
            }
        }) {
            Text("Forgot Password?")
        }

        Spacer(Modifier.height(30.px))
            Button(
                onClick = {
                    emailError = validateEmail(email)
                    passwordError = validatePassword(password)

                    if (emailError.isEmpty() && passwordError.isEmpty()) {
                        println("onSignInClick()")
                        onSignInClick(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth().backgroundColor(Color("#007BFF"))
                    .color(Colors.White).borderRadius(8.px).padding(12.px)
            ) {
                Box() {
                SpanText("Sign in", modifier = Modifier.fontSize(18.px))
                }
        }

        Spacer(Modifier.height(20.px))

        SpanText("O/R", modifier = Modifier.color(Colors.Gray).margin(bottom = 20.px))

        Button(
            onClick = { /* Handle Google Login */ },
            modifier = Modifier.fillMaxWidth().backgroundColor(Colors.White).border(1.px,
                LineStyle.Solid, Colors.LightGray
            ).borderRadius(8.px).padding(12.px).margin(bottom = 15.px)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Img(src = "/google-logo.svg", alt = "Google Logo", attrs = {
                    style {
                        width(20.px)
                        height(20.px)
                        marginRight(10.px)
                    }
                })
                SpanText("Continue with Google", modifier = Modifier.color(Colors.Black).fontSize(16.px))
            }
        }

        Button(
            onClick = { /* Handle Apple Login */ },
            modifier = Modifier.fillMaxWidth().backgroundColor(Colors.White).border(1.px,
                LineStyle.Solid, Colors.LightGray
            ).borderRadius(8.px).padding(12.px)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Img(src = "/apple-logo.svg", alt = "Apple Logo", attrs = {
                    style {
                        width(20.px)
                        height(20.px)
                        marginRight(10.px)
                    }
                })
                SpanText("Continue with Apple", modifier = Modifier.color(Colors.Black).fontSize(16.px))
            }
        }

        Spacer(Modifier.height(30.px))

        Row(
            modifier = Modifier.fillMaxWidth(), // Ensure the Row takes full width
            horizontalArrangement = Arrangement.Center
        ) {
            SpanText("Don't have an Account?", modifier = Modifier.color(Colors.Gray))
            A(href = "#", attrs = {
                style {
                    color(Color("#007BFF"))
                    marginLeft(5.px)
                }
                onClick {
                    it.preventDefault() // Prevents the browser from navigating away
                    onForgotPasswordClick() // Call the lambda to show the dialog
                }
            }) {
                Text("Sign Up")
            }
        }
    }
}