package com.ours.statustracker.pages

import androidx.compose.runtime.*
import com.stevdza.san.kotlinbs.components.BSIconButton
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.forms.BSSwitch
import com.stevdza.san.kotlinbs.icons.BSIcons
import com.stevdza.san.kotlinbs.models.InputValidation
import com.stevdza.san.kotlinbs.models.button.ButtonVariant
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    Column {
        var isChecked by remember { mutableStateOf(false) }

        BSSwitch(
            label = "Example Switch",
            checked = isChecked,
            onClick = {
                isChecked = it
            }
        )

        Column(modifier = Modifier.gap(20.px).fillMaxSize()) {
            Row(modifier = Modifier.gap(12.px)) {
                BSIconButton(
                    icon = BSIcons.UPLOAD,
                    onClick = {}
                )
                BSIconButton(
                    icon = BSIcons.UPLOAD,
                    variant = ButtonVariant.PrimaryOutline,
                    onClick = {}
                )
            }
            Row(modifier = Modifier.gap(12.px)) {
                BSIconButton(
                    icon = BSIcons.ANDROID,
                    variant = ButtonVariant.Success,
                    onClick = {}
                )
                BSIconButton(
                    icon = BSIcons.ANDROID,
                    variant = ButtonVariant.SuccessOutline,
                    onClick = {}
                )
            }

            var inputValue by remember { mutableStateOf("") }
            BSInput(
                value = inputValue,
                placeholder = "Type here",
                onValueChange = {
                    inputValue = it
                }
            )

            BSInput(
                value = inputValue,
                label = "Email Address 1",
//                floating = true,
                onValueChange = {
                    inputValue = it
                }
            )

            BSInput(
                value = inputValue,
                label = "Email Address 2",
                placeholder = "Type here",
                validation = InputValidation(
                    isValid = true
                ),
                onValueChange = {
                    inputValue = it
                }
            )
        }
    }
}