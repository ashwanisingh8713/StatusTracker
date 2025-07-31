package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.utils.Spacer
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Page(Redirection.SIGN_UP)
@Composable
fun SignupPage() {
    val breakpoint = rememberBreakpoint()
    val isMobile = breakpoint <= Breakpoint.MD
    var signUpUIState by remember{ mutableStateOf(SignUpUIState()) }
    val spacerHeight = 15.px

    Box(modifier = if (isMobile) Modifier.width(300.px) else Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            // Sign Up Text
            SpanText(text = "Sign up", modifier = Modifier.fontSize(25.px))

            // Spacer
            Spacer(modifier = Modifier.height(spacerHeight))

            // Sign up to continue UI
            SpanText(text = "Sign up to continue", modifier = Modifier.fontSize(14.px))

            // Spacer
            Spacer(modifier = Modifier.height(spacerHeight))

            // First Name
            Input(
                modifier = Modifier.fillMaxWidth(),
                type = InputType.Text,
                placeholder = "First Name",
                value = signUpUIState.firstName,
                onValueChange = {
                    signUpUIState = signUpUIState.copy(firstName = it)
                })

            // Spacer
            Spacer(modifier = Modifier.height(spacerHeight))

            // Last Name
            Input(
                modifier = Modifier.fillMaxWidth(),
                type = InputType.Text,
                placeholder = "Last Name",
                value = signUpUIState.lastName,
                onValueChange = {
                    signUpUIState = signUpUIState.copy(firstName = it)
                })

            // Spacer
            Spacer(modifier = Modifier.height(spacerHeight))

            // Email
            Input(
                modifier = Modifier.fillMaxWidth(),
                type = InputType.Email,
                placeholder = "Email",
                value = signUpUIState.lastName,
                onValueChange = {
                    signUpUIState = signUpUIState.copy(firstName = it)
                })

            // Spacer
            Spacer(modifier = Modifier.height(spacerHeight))

            // Password
            Input(
                modifier = Modifier.fillMaxWidth(),
                type = InputType.Password,
                placeholder = "Password",
                value = signUpUIState.lastName,
                onValueChange = {
                    signUpUIState = signUpUIState.copy(firstName = it)
                })

            // Spacer
            Spacer(modifier = Modifier.height(spacerHeight))

            // Confirm Password
            Input(
                modifier = Modifier.fillMaxWidth(),
                type = InputType.Password,
                placeholder = "Confirm Password",
                value = signUpUIState.lastName,
                onValueChange = {
                    signUpUIState = signUpUIState.copy(firstName = it)
                })

        }

        val onSubmitBtnClick:(uiState: SignUpUIState)-> Unit = {it->
            // TODO, Handle submit button click
        }

        // Submit Button Click
        Button(
            onClick = {onSubmitBtnClick(signUpUIState)}
        ) {
            Text("Submit")
        }

    }
}

// This is UI state handling data class
data class SignUpUIState(val firstName: String = "", val lastName: String = "", val email: String = "", val password: String = "")