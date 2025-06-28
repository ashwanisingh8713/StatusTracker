// src/jsMain/kotlin/com/indusjs/statustracker/pages/LoginPage.kt
package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.indusjs.statustracker.components.LoginSection
import com.indusjs.statustracker.components.MarketingSection
import com.indusjs.statustracker.components.ForgotPasswordDialog
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column as KobwebColumn
import com.varabyte.kobweb.compose.foundation.layout.Row as KobwebRow
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import org.jetbrains.compose.web.css.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
// CORRECTED IMPORTS: rememberBreakpoint from theme, Breakpoint from style
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint


@Page("/login") // This defines the root URL for your login page
@Composable
fun LoginPage() {
    val breakpoint = rememberBreakpoint()
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    KobwebColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (breakpoint >= Breakpoint.MD) {
            KobwebRow(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.weight(0.4f).fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    LoginSection(onForgotPasswordClick = { showForgotPasswordDialog = true })
                }
                Box(
                    modifier = Modifier.weight(0.6f).fillMaxHeight()
                        .backgroundColor(Color("#29323C")) // Dark background color
                ) {
                    MarketingSection()
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                LoginSection(onForgotPasswordClick = { showForgotPasswordDialog = true })
            }
        }
    }

    ForgotPasswordDialog(
        isVisible = showForgotPasswordDialog,
        onDismissRequest = {
            showForgotPasswordDialog = false
        },
        onSendResetLink = { emailToReset ->
            println("Forgot password request initiated for: $emailToReset")
        }
    )
}