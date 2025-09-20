package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.indusjs.statustracker.utils.LinearGradient
import com.stevdza.san.kotlinbs.components.BSAlert
import com.stevdza.san.kotlinbs.components.BSButton
import com.stevdza.san.kotlinbs.components.BSDropdown
import com.stevdza.san.kotlinbs.components.BSIconButton
import com.stevdza.san.kotlinbs.components.BSModal
import com.stevdza.san.kotlinbs.components.BSToast
import com.stevdza.san.kotlinbs.components.BSToastGroup
import com.stevdza.san.kotlinbs.components.showModalOnClick
import com.stevdza.san.kotlinbs.components.showToast
import com.stevdza.san.kotlinbs.forms.BSCheckbox
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.forms.BSRadioButton
import com.stevdza.san.kotlinbs.forms.BSRadioButtonGroup
import com.stevdza.san.kotlinbs.forms.BSSwitch
import com.stevdza.san.kotlinbs.forms.BSTextArea
import com.stevdza.san.kotlinbs.icons.BSIcons
import com.stevdza.san.kotlinbs.models.AlertIcon
import com.stevdza.san.kotlinbs.models.BSBorderRadius
import com.stevdza.san.kotlinbs.models.InputValidation
import com.stevdza.san.kotlinbs.models.button.ButtonBadge
import com.stevdza.san.kotlinbs.models.button.ButtonCustomization
import com.stevdza.san.kotlinbs.models.button.ButtonVariant
import com.varabyte.kobweb.compose.css.functions.linearGradient
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.rgb
import org.jetbrains.compose.web.css.rgba

@Page("/bt")
@Composable
fun ShowBT() {
    BSButton(
        text = "Sign in",
        onClick = {}
    )
    var buttonLoading by remember { mutableStateOf(false) }
    BSButton(
        text = "Sign in",
        loading = buttonLoading,
        loadingText = "Please wait...",
        onClick = { buttonLoading = true }
    )
    BSButton(
        text = "Shopping Cart",
        badge = ButtonBadge(
            text = "13"
        ),
        onClick = {}
    )
    BSButton(
        text = "Shopping Cart",
        badge = ButtonBadge(
            text = "15"
        ),
        onClick = {}
    )
    Column(modifier = Modifier.gap(20.px).fillMaxSize()) {
        Row(modifier = Modifier.gap(12.px)) {
            BSButton(
                text = "Apply Now",
                customization = ButtonCustomization(
                    color = Colors.White,
                    hoverColor = Colors.White,
                    backgroundColor = Colors.Black,
                    hoverBackgroundColor = rgba(0, 0, 0, 0.8),
                    fontFamily = "Space Grotesk"
                ),
                onClick = {}
            )
            BSButton(
                text = "Get Started",
                customization = ButtonCustomization(
                    color = Colors.White,
                    hoverColor = Colors.White,
                    activeColor = Colors.WhiteSmoke,
                    borderColor = Colors.White,
                    hoverBorderColor = Colors.White,
                    activeBorderColor = rgb(168, 192, 255),
                    gradient = linearGradient(
                        from = rgb(168, 192, 255),
                        to = rgb(63, 43, 150),
                        //dir = LinearGradient.Direction.ToTopRight
                    ),
                    borderRadius = BSBorderRadius(all = 50.px),
                    horizontalPadding = 1.25.cssRem
                ),
                onClick = {}
            )
        }
        Row(modifier = Modifier.gap(12.px)) {
            BSButton(
                text = "Submit",
                customization = ButtonCustomization(
                    color = Colors.White,
                    hoverColor = Colors.Wheat,
                    activeColor = Colors.White,
                    borderColor = Colors.White,
                    hoverBorderColor = Colors.Wheat,
                    activeBorderColor = Colors.White,
                    gradient = linearGradient(
                        from = rgb(188, 78, 156),
                        to = rgb(248, 7, 89),
                        //dir = LinearGradient.Direction.ToTopRight
                    ),
                    borderRadius = BSBorderRadius(topLeft = 20.px, bottomRight = 20.px),
                    fontFamily = "Rubik"
                ),
                onClick = {}
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
                        label = "Email Address",
                        floating = true,
                        onValueChange = {}
                    )
                    BSInput(
                        value = inputValue,
                        label = "Email Address",
                        placeholder = "Type here",
                        validation = InputValidation(
                            isValid = true
                        ),
                        onValueChange = {}
                    )
                    BSInput(
                        value = inputValue,
                        label = "Email Address",
                        placeholder = "Type here",
                        validation = InputValidation(
                            isInvalid = true
                        ),
                        onValueChange = {}
                    )
                    BSInput(
                        value = inputValue,
                        label = "Email Address",
                        placeholder = "Type here",
                        disabled = true,
                        onValueChange = {}
                    )
                    BSInput(
                        value = inputValue,
                        label = "Email Address",
                        placeholder = "Type here",
                        plainText = true,
                        onValueChange = {}
                    )
                    BSDropdown(
                        placeholder = "Select a Platform",
                        items = listOf("Android", "iOS", "Web"),
                        onItemSelect = { index, value -> }
                    )
                }
                BSDropdown(
                    items = listOf("Android", "iOS", "Web"),
                    darkBackground = true,
                    onItemSelect = { index, value -> }
                )
            }
            BSDropdown(
                items = listOf("Android", "iOS", "Web"),
                disabledItems = listOf("iOS"),
                onItemSelect = { index, value -> }
            )
        }
    }
    var value by remember { mutableStateOf("") }
    BSTextArea(
        value = value,
        label = "Email Address",
        placeholder = "Type here...",
        onValueChange = { value = it }
    )
    BSCheckbox(
        label = "Kotlin",
        onClick = {}
    )
    BSCheckbox(
        label = "Vijay",
        reverse = true,
        onClick = {}
    )
    BSCheckbox(
        label = "Nilesh",
        toggleButton = true,
        onClick = {}
    )
    BSRadioButtonGroup {
        BSRadioButton(label = "Android", onClick = {})
        BSRadioButton(label = "iOS", onClick = {})
        BSRadioButton(label = "Web", onClick = {})
    }
    BSSwitch(
        label = "Android",
        disabled = true,
        onClick = {}
    )
    BSAlert(
        message = "Visit my YouTube Channel: Stevdza-San",
        icon = AlertIcon.Info,
        bold = "Stevdza-San"
    )
    BSToastGroup {
        BSToast(
            id = "toast",
            title = "Welcome",
            body = "Browse our website for more interesting products!",
            onCloseClick = {}
        )
    }

    BSButton(
        text = "Show Toast",
        onClick = {
            showToast("toast")
        }
    )
    BSModal(
        id = "contactModal",
        title = "Contact us",
        body = {
            Column {
                BSInput(
                    modifier = Modifier
                        .fillMaxWidth()
                        .margin(bottom = 14.px),
                    value = "",
                    label = "Email Address",
                    placeholder = "Type here...",
                    onValueChange = {}
                )
                BSTextArea(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    label = "Message",
                    placeholder = "Type here...",
                    onValueChange = {}
                )
            }
        },
        positiveButtonText = "Send Message",
        negativeButtonText = "Close",
        onPositiveButtonClick = {},
        onNegativeButtonClick = {}
    )

    BSButton(
        modifier = Modifier.showModalOnClick(id = "contactModal"),
        text = "Trigger",
        onClick = {}
    )
}
