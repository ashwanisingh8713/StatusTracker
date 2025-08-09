package com.ours.statustracker.pages

import androidx.compose.runtime.*
import com.stevdza.san.kotlinbs.components.BSButton
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Img
import com.stevdza.san.kotlinbs.forms.BSCheckbox
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.models.InputValidation
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.css.margin
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text

@Page("/abc")
@Composable
fun BT_SignUpPage() {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    // Validation rules
    val emailInvalid = email.isNotEmpty() && (!email.contains("@") || !email.contains("."))
    val passwordInvalid = password.isNotEmpty() && password.length < 6
    val confirmPasswordInvalid = confirmPassword.isNotEmpty() && confirmPassword != password
    val fullNameInvalid = fullName.isNotEmpty() && fullName.length < 2

    val formValid = !emailInvalid && !passwordInvalid && !confirmPasswordInvalid && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Colors.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .maxWidth(if (breakpoint <= Breakpoint.SM) 90.percent else 35.percent)
                .background(Colors.White)
                .padding(32.px)
                .borderRadius(8.px)
                .boxShadow(
                    offsetX = 0.px,
                    offsetY = 4.px,
                    blurRadius = 16.px,
                    color = Colors.Gray
                )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Company Logo
                Img(
                    src = "/logo.png",
                    alt = "Company Logo",
                    attrs = {
                        style {
                            width(72.px)
                            height(72.px)
                            margin(bottom = 16.px)
                        }
                    }
                )

                // Title
                H3(
                    attrs = {
                        style {
                            margin(bottom = 24.px)
                            fontSize(24.px)
                            fontWeight(FontWeight.Bold)
                        }
                    }
                ) {
                    Text("Sign Up")
                }

                // Google Sign Up Button (single button, icon+text)
                /*BSButton(
                    onClick = { *//* Google OAuth *//* },
                    modifier = Modifier.fillMaxWidth().margin(bottom = 24.px)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Img(
                            src = "/G_ic.png",
                            alt = "Google Icon",
                            attrs = {
                                style {
                                    width(20.px)
                                    height(20.px)
                                    margin(right = 8.px)
                                }
                            }
                        )
                        SpanText("Sign Up with Google")
                    }
                }*/
                // Sign Up with Google Button
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.px),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().margin(bottom = 12.px)
                ) {
                    BSButton(
                        text = "Google SignUp",
                        onClick = {
                            // TODO: Trigger Google OAuth flow
                        },
                        modifier = Modifier
                            .width(120.px)
                            .height(40.px)
                    )
                    Img(
                        src = "/G_ic.png",
                        alt = "Google Icon",
                        attrs = {
                            style {
                                width(20.px)
                                height(20.px)
                            }
                        }
                    )
                }

                // Full Name
                BSInput(
                    value = fullName,
                    label = "Full Name (optional)",
                    placeholder = "Enter your full name",
                    validation = InputValidation(
                        isInvalid = fullNameInvalid,
                        invalidFeedback = "Please enter a valid name."
                    ),
                    onValueChange = { fullName = it },
                    modifier = Modifier.fillMaxWidth().margin(bottom = 16.px)
                )

                // Email
                BSInput(
                    value = email,
                    label = "Email",
                    placeholder = "Enter your email",
                    validation = InputValidation(
                        isInvalid = emailInvalid,
                        invalidFeedback = "Please enter a valid email."
                    ),
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().margin(bottom = 16.px)
                )

                // Password
                BSInput(
                    value = password,
                    label = "Password",
                    placeholder = "Enter your password",
                    type = InputType.Password,
                    validation = InputValidation(
                        isInvalid = passwordInvalid,
                        invalidFeedback = "Password must be at least 6 characters."
                    ),
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().margin(bottom = 16.px)
                )

                // Confirm Password
                BSInput(
                    value = confirmPassword,
                    label = "Confirm Password",
                    placeholder = "Re-enter your password",
                    type = InputType.Password,
                    validation = InputValidation(
                        isInvalid = confirmPasswordInvalid,
                        invalidFeedback = "Passwords do not match."
                    ),
                    onValueChange = { confirmPassword = it },
                    modifier = Modifier.fillMaxWidth().margin(bottom = 24.px)
                )

                // Terms and Conditions
                BSCheckbox(
                    label = "I agree to the Terms and Conditions",
                    defaultChecked = termsAccepted,
                    onClick = { checked -> termsAccepted = checked },
                    modifier = Modifier.margin(bottom = 24.px)
                )

                // Sign Up Button
                BSButton(
                    text = "Sign Up",
                    loading = loading,
                    loadingText = "Signing Up...",
                    disabled = loading || !termsAccepted || !formValid,
                    onClick = {
                        loading = true
                        scope.launch {
                            delay(2000) // Simulate API call
                            loading = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}