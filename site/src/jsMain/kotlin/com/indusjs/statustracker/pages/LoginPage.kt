// src/jsMain/kotlin/com/indusjs/statustracker/pages/LoginPage.kt
package com.indusjs.statustracker.pages

// CORRECTED IMPORTS: rememberBreakpoint from theme, Breakpoint from style
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.indusjs.di.initKoin
import com.indusjs.statustracker.components.ForgotPasswordDialog
import com.indusjs.statustracker.components.LoginSection
import com.indusjs.statustracker.components.MarketingSection
import com.indusjs.statustracker.components.layouts.PageLayoutData
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.viewmodel.SignInViewModule
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.Color
import org.koin.compose.getKoin
import com.varabyte.kobweb.compose.foundation.layout.Column as KobwebColumn
import com.varabyte.kobweb.compose.foundation.layout.Row as KobwebRow


@InitRoute
fun initLoginPage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Loginn"))
}

@Page("/login")
@Composable
fun LoginPage(ctx: PageContext) {
    val breakpoint = rememberBreakpoint()
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    // Getting SignInViewModel
    val signInViewModel: SignInViewModule = getKoin().get<SignInViewModule>()

    signInViewModel.getCoroutineScope.launch {
        signInViewModel.loginState.collectLatest {
            when(it.signInResponse) {
                is ResourceUiState.Success -> {
                    println("Login Success")
                }
                is ResourceUiState.Error -> {
                    println("Login Error")
                }
                is ResourceUiState.Idle -> {
                    println("Login Idle")
                }
                is ResourceUiState.Empty -> {
                    println("Login Empty")
                }
                is ResourceUiState.Loading -> {
                    println("Login Loading")
                }
            }
        }
    }

    val onSignInClick: (email: String, password: String) -> Unit = { email, password ->
        signInViewModel.signInRequest(email, password)
    }

    KobwebColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        if (breakpoint >= Breakpoint.MD) {
            KobwebRow(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.weight(0.4f).fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    LoginSection(onForgotPasswordClick = { showForgotPasswordDialog = true }, onSignInClick = onSignInClick)
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
                LoginSection(onForgotPasswordClick = { showForgotPasswordDialog = true }, onSignInClick = onSignInClick)
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