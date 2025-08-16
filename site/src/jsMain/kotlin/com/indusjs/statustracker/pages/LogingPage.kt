package com.indusjs.statustracker.pages
/*
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.dom.svg.Text
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.textAlign
import com.varabyte.kobweb.compose.ui.modifiers.whiteSpace
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Span


@Page
@Composable
fun HomePage() {
    Column(
        Modifier
            .fillMaxWidth().whiteSpace(WhiteSpace.PreWrap).textAlign(TextAlign.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var colorMode by ColorMode.currentState
        Button(
            onClick = { colorMode = colorMode.opposite },
            Modifier.borderRadius(50.percent).padding(0.px).align(Alignment.End)
        ) {
            // Includes support for Font Awesome icons
            if (colorMode.isLight) FaMoon() else FaSun()
        }
        H1 {
            Text("Welcome to Kobweb!")
        }
        Span {
            Text("Create rich, dynamic web apps with ease, leveraging ")
            Link("https://kotlinlang.org/", "Kotlin")
            Text(" and ")
            Link(
                "https://github.com/JetBrains/compose-multiplatform/#compose-html",
                "Compose HTML"
            )
        }
    }
}*/



import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.WhiteSpace
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.icons.fa.FaMoon
import com.varabyte.kobweb.silk.components.icons.fa.FaSun
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.H1
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text   // ✅ yahi sahi import hai

@Page
@Composable
fun HomePage() {
    Column(
        Modifier
            .fillMaxWidth()
            .whiteSpace(WhiteSpace.PreWrap)
            .textAlign(TextAlign.Center),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var colorMode by ColorMode.currentState
        Button(
            onClick = { colorMode = colorMode.opposite },
            Modifier.borderRadius(50.percent).padding(0.px).align(Alignment.End)
        ) {
            if (colorMode.isLight) FaMoon() else FaSun()
        }
        H1 {
            Text("Welcome to Kobweb!")
        }
        Span {
            Text("Create rich, dynamic web apps with ease, leveraging ")
            Link("https://kotlinlang.org/", "Kotlin")
            Text(" and ")
            Link("https://github.com/JetBrains/compose-multiplatform/#compose-html", "Compose HTML")
        }
    }
}
