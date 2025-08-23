package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.utils.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.silk.components.forms.Button
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page("/welcome")
@Composable
fun WelcomePage(ctx: PageContext) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.px) // your hero height
        ) {
            Img(
                src = "hero.png", // path in your site/public folder
                alt = "Hero Image",
                attrs = Modifier.fillMaxSize().toAttrs()
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .backgroundColor(Colors.Black.copy(alpha = 255)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(2.cssRem)
            ) {
                H1 {
                    Modifier.color(Colors.White).fontSize(3.cssRem)
                    Text("Welcome to StatusTracker")
                }
                Spacer(Modifier.height(16.px))
                P {
                    Modifier.color(Colors.LightGray)
                    Text("Track your daily work logs and boost productivity.")
                }
                Spacer(Modifier.height(32.px))

                Button(
                    onClick = { ctx.router.navigateTo(Redirection.LOGIN) },
                    modifier = Modifier
                        .backgroundColor(Color.rgb(255, 87, 34))
                        .color(Colors.White)
                        .padding(0.75.cssRem, 0.5.cssRem)
                        .borderRadius(0.5.cssRem)
                ) {
                    Text("Login")
                }

                Spacer(Modifier.height(16.px))

                Button(
                    onClick = { ctx.router.navigateTo("nilesh_calculator_page") },
                    modifier = Modifier
                        .border(1.px, LineStyle.Solid, Colors.White)
                        .color(Colors.White)
                        .backgroundColor(Colors.Transparent)
                        .padding(0.75.cssRem, 0.5.cssRem)
                        .borderRadius(0.5.cssRem)
                ) {
                    Text("Vocabulary")
                }
            }
        }
    }
}