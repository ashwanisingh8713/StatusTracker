package com.indusjs.statustracker.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.indusjs.data.auth.AuthManager
import com.indusjs.statustracker.AppStyle
import com.indusjs.statustracker.AppStyles
import com.indusjs.statustracker.AppStyles.COLOR_LABEL_TEXT
import com.indusjs.statustracker.utils.Cursor.Companion.Text
import com.indusjs.statustracker.utils.Redirection
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
//import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.boxShadow
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.minWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.right
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.compose.ui.toAttrs
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLElement
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.layout.HorizontalDivider
import com.varabyte.kobweb.silk.components.text.SpanText

@Composable
fun ShowOptionMenu(isFromLogList: Boolean, ctx: PageContext, modifier: Modifier = Modifier) {
    VerticalThreeDotMenu(
        menuItems = if(isFromLogList) listOf("Work Log Entry", "Logout") else listOf("All Work Logs", "Logout"),
        modifier = modifier,
        onMenuItemClick = { item ->
            when(item) {
                "Work Log Entry" -> {ctx.router.navigateTo(Redirection.DAILY_WORK_LOG)}
                "All Work Logs" -> {ctx.router.navigateTo(Redirection.DASHBOARD)}
                "Logout" -> {
                    AuthManager.signOut()
                    ctx.router.navigateTo(Redirection.LOGIN)
                }
            }
            println("Menu item clicked: $item")
            // In a real app, you'd handle the action here
            // e.g., show a dialog, navigate, etc.
        }
    )
}

// This composable represents a single item in the dropdown menu
@Composable
fun DropdownMenuItem(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Div(
        // Use Modifier for styling and onClick directly on the Div composable
        attrs = modifier
            .width(100.percent)
            .cursor(Cursor.Pointer)
            .fontSize(16.px)
            .color(AppStyles.COLOR_INPUT_TEXT)
            .backgroundColor(AppStyles.COLOR_INPUT_BACKGROUND)
            .borderRadius(4.px)
            .onClick { onClick() }.toAttrs() // Handle click directly on Modifier
    ) {
        SpanText(
            text,
            modifier = Modifier
                .fontSize(12.px)
                .margin(top = 4.px, bottom = 4.px)
                .color(COLOR_LABEL_TEXT)
                .fontWeight(FontWeight.SemiBold)
        )
        HorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

// This is the main composable for the vertical three-dot menu
@Composable
fun VerticalThreeDotMenu(
    // A list of items to display in the dropdown
    menuItems: List<String>,
    modifier: Modifier,
    // Callback when a menu item is clicked
    onMenuItemClick: (String) -> Unit
) {
    // State to control the visibility of the dropdown menu
    var expanded by remember { mutableStateOf(false) }

    // State to hold the reference to the element that triggers the dropdown
    // This is useful for positioning the dropdown relative to the button
    var anchorElement by remember { mutableStateOf<HTMLElement?>(null) }

    Column(
        modifier = modifier//Modifier.position(Position.Relative) // Important for positioning the dropdown
        // .toAttrs() is removed here as Modifier is passed directly to Column
    ) {

        // The three-dot icon button
        Button(
            // onClick is a direct parameter of Button composable
            onClick = { expanded = !expanded },
            modifier = Modifier
                .backgroundColor(Color.transparent)
                .border(0.px)
//                .padding(4.px)
                .cursor(Cursor.Pointer)
                .fontSize(24.px) // Make dots larger
               // .color(Color("#666")) // Darker gray for dots
                .borderRadius(50.percent)
        ) {
            SpanText(
                "⋮",
                modifier = Modifier
                    .fontSize(25.px)
                    .margin(top = 10.px, bottom = 15.px)
                    .color(COLOR_LABEL_TEXT)
                    .fontWeight(FontWeight.SemiBold)
            )
        }

        // The actual dropdown menu
        if (expanded) {
            // Scrim for detecting outside clicks
            Div(
                attrs = Modifier
                    .position(Position.Fixed) // Fixed to cover the entire viewport
                    .top(0.px).left(0.px).right(0.px).bottom(0.px) // Cover full screen
                    .zIndex(999) // Lower than the menu, higher than other content
                    .onClick { expanded = false }
                    .toAttrs()// Close menu when scrim is clicked
                // Optional: Add a transparent background to make it visible during debugging
                // .backgroundColor(Color("rgba(0,0,0,0.0)"))
            ) {} // Empty Div for the scrim
            // This Div acts as the dropdown container
            Div(
                attrs = Modifier
                    .position(Position.Absolute) // Position relative to parent Column
                    .top(anchorElement?.offsetHeight?.px ?: 0.px) // Position below the button
                    .right(0.px) // Align to the right of the button
                    .zIndex(1000) // Ensure it's above other content
//                    .backgroundColor(Color.white)
                    .borderRadius(8.px)
                    .boxShadow(0.px, 4.px, 8.px, 0.px, Color("rgba(0,0,0,0.2)"))
                    .padding(8.px)
                    .minWidth(150.px)
                 .toAttrs() //is removed here as Modifier is passed directly to Div
            ) {
                Column {
                    menuItems.forEach { item ->
                        DropdownMenuItem(
                            text = item,
                            onClick = {
                                onMenuItemClick(item)
                                expanded = false // Close menu after selection
                            }
                        )
                    }
                }
            }
        }
    }

    // Optional: Add a click listener to close the menu when clicking outside
    // This requires a bit more advanced DOM interaction in Compose Web
    // For simplicity, we'll rely on menu item click to close it for now.
    // In a full Kobweb app, you might use a scrim or a global click handler.
}