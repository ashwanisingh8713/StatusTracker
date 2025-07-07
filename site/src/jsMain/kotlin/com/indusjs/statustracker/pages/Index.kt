package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.indusjs.data.auth.AuthManager
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.data.add
import com.varabyte.kobweb.core.init.InitRoute
import com.varabyte.kobweb.core.init.InitRouteContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.toModifier
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div
import com.indusjs.statustracker.components.layouts.PageLayoutData
import com.indusjs.statustracker.components.LoginSection
import com.indusjs.statustracker.components.MarketingSection
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.utils.rememberWindowWidth
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.css.*
// MAKE SURE THESE TWO IMPORTS ARE PRESENT:
import com.varabyte.kobweb.compose.foundation.layout.Column as KobwebColumn
import com.varabyte.kobweb.compose.foundation.layout.Row as KobwebRow


// Container that has a tagline and grid on desktop, and just the tagline on mobile
val HeroContainerStyle = CssStyle {
    base { Modifier.fillMaxWidth().gap(2.cssRem) }
    Breakpoint.MD { Modifier.margin { top(20.vh) } }
}

// A demo grid that appears on the homepage because it looks good
val HomeGridStyle = CssStyle.base {
    Modifier
        .gap(0.5.cssRem)
        .width(70.cssRem)
        .height(18.cssRem)
}

private val GridCellColorVar by StyleVariable<Color>()
val HomeGridCellStyle = CssStyle.base {
    Modifier
        .backgroundColor(GridCellColorVar.value())
        .boxShadow(blurRadius = 0.6.cssRem, color = GridCellColorVar.value())
        .borderRadius(1.cssRem)
}

@Composable
private fun GridCell(color: Color, row: Int, column: Int, width: Int? = null, height: Int? = null) {
    Div(
        HomeGridCellStyle.toModifier()
            .setVariable(GridCellColorVar, color)
            .gridItem(row, column, width, height)
            .toAttrs()
    )
}


@InitRoute
fun initHomePage(ctx: InitRouteContext) {
    ctx.data.add(PageLayoutData("Home"))
}



@Page
@Layout(".components.layouts.PageLayout")
@Composable
fun HomePage(ctx: PageContext) {
    if (AuthManager.isSignedIn()) {
        println("User is signed in")
        ctx.router.navigateTo(Redirection.DAILY_WORK_LOG)
    } else {
        ctx.router.navigateTo(Redirection.LOGIN) // Navigate to a protected page
        println("User is not signed in")
    }
}



/*@Composable
fun LoginPage() {
    val windowWidth by rememberWindowWidth()
    val DESKTOP_BREAKPOINT = 768 // Pixels

    val isDesktop = windowWidth >= DESKTOP_BREAKPOINT

    Box(
        modifier = Modifier.fillMaxSize()
            .backgroundColor(Colors.White) // Background color for the entire page
    ) {
        if (isDesktop) {
            // Desktop view: Show both Login and Marketing sections side-by-side
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.flexGrow(1) // Takes up half the available width
                        .backgroundColor(Colors.White),
                    contentAlignment = Alignment.Center // <<< --- CRUCIAL CHANGE: Center LoginSection within this Box
                ) {
                    LoginSection()
                }
                Box(modifier = Modifier.flexGrow(1).backgroundColor(Color("#ff0000"))) {
                    MarketingSection()
                }
            }
        } else {
            // Mobile view: Only show the Login section
            Box(
                modifier = Modifier.fillMaxSize(), // Fill the entire page
                contentAlignment = Alignment.Center // <<< --- CRUCIAL CHANGE: Center LoginSection for mobile
            ) {
                LoginSection()
            }
        }
    }
}*/






