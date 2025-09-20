package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.*
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.percent

@Page("/nilu")
@Composable
fun CalculatorPage() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.px),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Display
        SpanText(
            if (result.isNotEmpty()) "Result: $result" else input,
            modifier = Modifier
                .fillMaxWidth()
                .margin(bottom = 20.px)

        )

        // Buttons Grid
        Column(modifier = Modifier.width(200.px)) {
            val buttons = listOf(
                listOf("7", "8", "9", "/"),
                listOf("4", "5", "6", "*"),
                listOf("1", "2", "3", "-"),
                listOf("0", "C", "=", "+")
            )

            buttons.forEach { row ->
                Row(
                    modifier = Modifier.fillMaxWidth().margin(bottom = 8.px),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    row.forEach { label ->
                        Button(
                            onClick = {
                                when (label) {
                                    "C" -> {
                                        input = ""
                                        result = ""
                                    }
                                    "=" -> {
                                        result = evaluateExpression(input)
                                    }
                                    else -> {
                                        input += label
                                    }
                                }
                            },
                            modifier = Modifier.width(40.px).height(40.px)
                        ) {
                            SpanText(label)
                        }
                    }
                }
            }
        }
    }
}

// Helper function to evaluate expression
fun evaluateExpression(expr: String): String {
    return try {
        val result = js("eval(expr)") as Double  // Using JS eval for simplicity
        if (result % 1 == 0.0) result.toInt().toString() else result.toString()
    } catch (e: Exception) {
        "Error"
    }
}
