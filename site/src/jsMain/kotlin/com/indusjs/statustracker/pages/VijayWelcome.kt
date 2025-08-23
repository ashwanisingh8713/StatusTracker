package com.indusjs.statustracker.pages



import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page(routeOverride ="/VijayWelcome")

@Composable
fun VijayWelcomePage() {
    Div({
        style {
            // Layout
            property("min-height", "100vh")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.Center)
            gap(20.px)

            // Spacing & look
            padding(24.px)
            background("linear-gradient(135deg, f8fafc 0%, eef2ff 100%)")
            color(rgb(17, 24, 39))
            property("font-family", "Inter, system-ui, -apple-system, Segoe UI, Roboto, Helvetica, Arial, sans-serif")
        }
    }) {
        H1({
            style {
                fontSize(40.px)
                margin(0.px)
                property("letter-spacing", "-0.02em")
            }
        }) { Text("Hello, Vijay ") }

        P({
            style {
                fontSize(18.px)
                margin(0.px)
                property("opacity", "0.85")
                textAlign("center")
                maxWidth(640.px)
            }
        }) {
            Text("Welcome to your Kobweb Composable page. You can start building your app from here.")
        }

        // Link back to home (optional)
        A("/"){ Text("Go to Home") }

        // A simple action button
        Button(attrs = {
            onClick { window.alert("Welcome, Vijay!") }
            style {
                padding(12.px, 18.px)
                borderRadius(12.px)
                border {
                    width(0.px)
                    style(LineStyle.None)
                }
                backgroundColor(rgb(79, 70, 229))
                color(rgb(255, 255, 255))
                property("cursor", "pointer")
                property("box-shadow", "0 8px 20px rgba(79, 70, 229, 0.25)")
            }
        }) { Text("Say Hi") }
    }
}
