package com.indusjs.statustracker

import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.ButtonStyle
import com.varabyte.kobweb.silk.components.forms.ButtonVars
import com.varabyte.kobweb.silk.components.layout.HorizontalDividerStyle
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.addVariantBase
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.theme.colors.palette.color
import com.varabyte.kobweb.silk.theme.colors.palette.toPalette
import com.varabyte.kobweb.silk.theme.modifyStyleBase
import org.jetbrains.compose.web.css.*

object AppStyles {
    // Colors
    val PrimaryColor = Color("#8EB0E1") // Green
    val SecondaryColor = Color("#8EB0E1") // Blue
    val BackgroundColor = Color("#8EB0E1") // Light Grey

    val TextColor = Color("#8EB0E1")// Dark Grey
    val BorderColor = Color("#8EB0E1")// Lighter Grey
    val SuccessColor = Color("#8EB0E1")
    val PendingColor = Color("#8EB0E1")
    val CompletedColor = Color("#8EB0E1")

    val COLOR_LABEL_TEXT = Color("#333333")
    val COLOR_INPUT_TEXT = Color("#333333")
    val COLOR_OUTPUT_TEXT = Color("#ffffff")
    val COLOR_INPUT_BACKGROUND = Color("#8EB0E1")
    val COLOR_CONTAINER_BACKGROUND = Color("#8EB0E1")
    val COLOR_CONTAINER_SHADOW = Color("#C4787C")
    val COLOR_INNER_CONTAINER_SHADOW = Color("#FFC44F")
    val COLOR_INPUT_BORDER = Color("#2D2D5A")

    // Font Sizes
    val FontSizeLarge = 1.5.em
    val FontSizeMedium = 1.2.em
    val FontSizeNormal = 1.0.em
    val FontSizeSmall = 0.8.em

    // Spacing/Sizes
    val PaddingDefault = 16.px
    val MarginDefault = 8.px
    val BorderRadiusDefault = 8.px
    val RowHeight = 80.px // Example fixed height for rows
}

@InitSilk
fun initSiteStyles(ctx: InitSilkContext) {
    // This site does not need scrolling itself, but this is a good demonstration for how you might enable this in your
    // own site. Note that we only enable smooth scrolling unless the user has requested reduced motion, which is
    // considered a best practice.
    ctx.stylesheet.registerStyle("html") {
        cssRule(CSSMediaQuery.MediaFeature("prefers-reduced-motion", StylePropertyValue("no-preference"))) {
            Modifier.scrollBehavior(ScrollBehavior.Smooth)
        }
    }

    ctx.stylesheet.registerStyleBase("body") {
        Modifier
            .fontFamily(
                "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Oxygen", "Ubuntu",
                "Cantarell", "Fira Sans", "Droid Sans", "Helvetica Neue", "sans-serif"
            )
            .fontSize(18.px)
            .lineHeight(1.5)
    }

    // Silk dividers only extend 90% by default; we want full width dividers in our site
    ctx.theme.modifyStyleBase(HorizontalDividerStyle) {
        Modifier.fillMaxWidth()
    }
}

val HeadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(3.cssRem)
        .textAlign(TextAlign.Start)
        .lineHeight(1.2) //1.5x doesn't look as good on very large text
}

val SubheadlineTextStyle = CssStyle.base {
    Modifier
        .fontSize(1.cssRem)
        .textAlign(TextAlign.Start)
        .color(colorMode.toPalette().color.toRgb().copyf(alpha = 0.8f))
}

val CircleButtonVariant = ButtonStyle.addVariantBase {
    Modifier.padding(0.px).borderRadius(50.percent)
}

val UncoloredButtonVariant = ButtonStyle.addVariantBase {
    Modifier.setVariable(ButtonVars.BackgroundDefaultColor, Colors.Transparent)
}
