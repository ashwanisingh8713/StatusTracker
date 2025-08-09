package com.ours.statustracker.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.stevdza.san.kotlinbs.components.BSButton
import com.stevdza.san.kotlinbs.forms.BSCheckbox
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.icons.BSIcons
import com.stevdza.san.kotlinbs.models.InputValidation
import com.stevdza.san.kotlinbs.models.button.ButtonVariant
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.cursor
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.css.margin
import com.varabyte.kobweb.compose.css.objectFit
import com.varabyte.kobweb.compose.css.textAlign
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page("/elegant-signup-pro") // New route for this version
@Composable
fun ElegantSignUpPagePro() {
    val breakpoint = rememberBreakpoint()
    val scope = rememberCoroutineScope()

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var termsAccepted by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    val emailInvalid =
        email.isNotEmpty() && (!email.contains("@") || !email.contains("."))
    val passwordInvalid = password.isNotEmpty() && password.length < 8
    val confirmPasswordInvalid = confirmPassword.isNotEmpty() && confirmPassword != password
    val fullNameInvalid = fullName.isNotEmpty() && fullName.trim().length < 2

    val isFormValid =
        email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() &&
                !emailInvalid && !passwordInvalid && !confirmPasswordInvalid &&
                (!fullName.isNotBlank() || !fullNameInvalid) &&
                termsAccepted

    val commonInputModifier = Modifier
        .fillMaxWidth()
        .margin(bottom = 16.px)

    val isDesktop = breakpoint >= Breakpoint.MD // Define our desktop breakpoint

    // Form Card Composable (to avoid repetition)
    @Composable
    fun SignUpFormCard(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .backgroundColor(Colors.White)
                .padding(if (isDesktop) 40.px else 24.px)
                .borderRadius(12.px)
                .boxShadow(
                    offsetX = 0.px,
                    offsetY = 8.px,
                    blurRadius = 24.px,
                    spreadRadius = (-4).px,
                    color = Color("rgba(0,0,0,0.1)")
                )
                .thenIf(!isDesktop, Modifier.margin(16.px)) // Margin for mobile card
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Img(
                    src = "/logo.png",
                    alt = "Company Logo",
                    attrs = { style {
                        width(if (breakpoint <= Breakpoint.SM) 60.px else 72.px)
                        height(if (breakpoint <= Breakpoint.SM) 60.px else 72.px)
                        margin(bottom = 24.px)
                    } }
                )
                H2(attrs = { style {
                    margin(bottom = 8.px)
                    fontSize(if (breakpoint <= Breakpoint.SM) 24.px else 28.px)
                    fontWeight(FontWeight.Bold); textAlign(TextAlign.Center); color(Color("#333"))
                } }) { Text("Create Your Account") }
                P(attrs = { style {
                    margin(bottom = 32.px)
                    fontSize(if (breakpoint <= Breakpoint.SM) 14.px else 16.px)
                    textAlign(TextAlign.Center); color(Color("#555"))
                } }) { Text("Join us and start your journey!") }

                BSInput(value = fullName, label = "Full Name (Optional)", placeholder = "Enter your full name", validation = InputValidation(isInvalid = fullNameInvalid, invalidFeedback = "Name should be at least 2 characters."), onValueChange = { fullName = it }, modifier = commonInputModifier)
                BSInput(value = email, label = "Email Address", placeholder = "you@example.com", type = InputType.Email, validation = InputValidation(isInvalid = emailInvalid, invalidFeedback = "Please enter a valid email address."), onValueChange = { email = it }, modifier = commonInputModifier)
                BSInput(value = password, label = "Password", placeholder = "Enter your password (min. 8 characters)", type = InputType.Password, validation = InputValidation(isInvalid = passwordInvalid, invalidFeedback = "Password must be at least 8 characters."), onValueChange = { password = it }, modifier = commonInputModifier)
                BSInput(value = confirmPassword, label = "Confirm Password", placeholder = "Re-enter your password", type = InputType.Password, validation = InputValidation(isInvalid = confirmPasswordInvalid, invalidFeedback = "Passwords do not match."), onValueChange = { confirmPassword = it }, modifier = commonInputModifier.margin(bottom = 24.px))
                BSCheckbox(label = "I agree to the Terms and Conditions", defaultChecked = termsAccepted, onClick = { termsAccepted = it }, modifier = Modifier.fillMaxWidth().margin(bottom = 24.px))
                BSButton(text = "Sign Up", loading = loading, loadingText = "Creating Account...", disabled = loading || !isFormValid, onClick = { if (isFormValid) { loading = true; scope.launch { delay(2000); loading = false } } }, modifier = Modifier.fillMaxWidth().height(48.px), variant = ButtonVariant.Primary)
                Row(modifier = Modifier.fillMaxWidth().margin(top = 24.px, bottom = 24.px), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Div(attrs = { style { height(1.px); flexGrow(1); backgroundColor(Color("#ddd")) } })
                    P(attrs = { style { margin(left = 16.px, right = 16.px); color(Color("#777")) }}) { Text("OR") }
                    Div(attrs = { style { height(1.px); flexGrow(1); backgroundColor(Color("#ddd")) } })
                }
                BSButton(text = "Sign Up with Google", onClick = { scope.launch { /* Simulate */ } }, modifier = Modifier.fillMaxWidth().margin(bottom = 24.px).height(48.px), variant = ButtonVariant.Light, icon = BSIcons.GOOGLE)
                Row(modifier = Modifier.margin(top = 0.px), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                    P(attrs = { style { color(Color("#555")); fontSize(14.px); margin(right = 4.px) }}) { Text("Already have an account?") }
                    P(attrs = { style { color(Colors.DodgerBlue); fontSize(14.px); fontWeight(FontWeight.SemiBold); cursor(Cursor.Pointer); textDecoration("none") } /* onClick for navigation */ }) { Text("Sign In") }
                }
            }
        }
    }

    // Banner Composable
    @Composable
    fun DesktopBanner(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .fillMaxHeight()
                .backgroundColor(Color("#007bff")) // Example background color
                .padding(40.px),
            contentAlignment = Alignment.Center
        ) {
            // Content for your banner
            // Example: Image and some text
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Img(
                    src = "/desktop-banner-image.png", // Replace with your banner image
                    alt = "Promotional Banner",
                    attrs = {
                        style {
                            maxWidth(300.px) // Limit image size
                            maxHeight(300.px)
                            objectFit(ObjectFit.Contain) // Ensure image fits well
                            borderRadius(8.px)
                            margin(bottom = 24.px)
                        }
                    }
                )
                H2(attrs = { style { color(Colors.White); textAlign(TextAlign.Center); margin(bottom = 16.px) } }) {
                    Text("Welcome Aboard!")
                }
                P(attrs = { style { color(Color("rgba(255,255,255,0.8)")); textAlign(TextAlign.Center) } }) {
                    Text("Join our community and unlock amazing features. Your journey starts now.")
                }
            }
        }
    }


    Box( // This outer Box will control the overall page background and structure
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(if (isDesktop) Colors.White else Color("rgb(240, 242, 245)")), // White bg for desktop to allow banner to have its own color
        contentAlignment = if (isDesktop) Alignment.CenterStart else Alignment.Center // Different alignment
    ) {
        if (isDesktop) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically // Center row content vertically
            ) {
                // Form Card takes a portion of the screen
                Box(
                    modifier = Modifier
                        .fillMaxHeight() // Make form container take full height
                        .weight(1f) // Adjust weight as needed (e.g., 50% for form)
                        .padding(leftRight = 40.px, topBottom = 20.px), // Padding around the form area
                    contentAlignment = Alignment.Center
                ) {
                    SignUpFormCard(
                        modifier = Modifier
                            .widthIn(min = 300.px, max = 500.px) // Max width for the form card itself
                            .fillMaxWidth()
                    )
                }
                // Banner takes the remaining portion
                DesktopBanner(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f) // Adjust weight (e.g., 50% for banner)
                )
            }
        } else {
            // Mobile view: Centered form card as before
            SignUpFormCard(
                modifier = Modifier
                    .widthIn(min = 300.px, max = 500.px) // Max width for mobile
                    .fillMaxWidth() // Take available width
            )
        }
    }
}
