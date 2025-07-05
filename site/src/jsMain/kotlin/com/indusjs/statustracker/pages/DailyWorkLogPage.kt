package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.indusjs.statustracker.AppStyle
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.utils.Resize
import com.indusjs.statustracker.utils.borderTop
import com.indusjs.statustracker.utils.fitContent
import com.indusjs.statustracker.utils.linearGradient
import com.indusjs.statustracker.utils.list
import com.indusjs.statustracker.utils.margin
import com.indusjs.statustracker.utils.repeat
import com.indusjs.statustracker.utils.resize
import com.indusjs.statustracker.utils.size
import com.indusjs.statustracker.utils.width
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.borderColor
import com.varabyte.kobweb.compose.css.gridTemplateRows
import com.varabyte.kobweb.compose.css.margin
import com.varabyte.kobweb.compose.css.scale
import com.varabyte.kobweb.compose.css.textAlign
// Removed specific margin import as general modifiers should cover it
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.common.PlaceholderColor
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Style
import org.jetbrains.compose.web.css.StyleSheet
import org.jetbrains.compose.web.css.alignItems
import org.jetbrains.compose.web.css.background
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.deg
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.em
import org.jetbrains.compose.web.css.flex
import org.jetbrains.compose.web.css.flexDirection
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.css.fontFamily
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.fontWeight
import org.jetbrains.compose.web.css.fr
import org.jetbrains.compose.web.css.gap
import org.jetbrains.compose.web.css.gridArea
import org.jetbrains.compose.web.css.gridTemplateColumns
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.keywords.auto
import org.jetbrains.compose.web.css.lineHeight
import org.jetbrains.compose.web.css.listStyle
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.maxWidth
import org.jetbrains.compose.web.css.media
import org.jetbrains.compose.web.css.mediaMaxWidth
import org.jetbrains.compose.web.css.minWidth
import org.jetbrains.compose.web.css.outline
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.paddingTop
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.css.selectors.Nth
import org.jetbrains.compose.web.css.style
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.css.textDecoration
import org.jetbrains.compose.web.css.transitions
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


// Data class to represent a single work log entry
data class WorkLogEntry(
    val id: String,
    val date: String,
    val task: String,
    val startTime: String,
    val endTime: String,
    val category: String,
    val status: String,
    val notes: String
)

// Constants for categories and statuses
val SUBJECTS = listOf(
    "Kotlin Fundamental", "GoLang Fundamental", "GoLang Data Structure",
    "Kotlin Data Structure", "Kobweb Fundamental", "Kobweb UI",
    "English Grammer", "Vocabulary"
)

val STATUSES = listOf("In progress", "Done", "Doubt", "ToDo")

/**
 * Custom Composable for a styled dropdown (Select) input.
 */
@Composable
fun DropdownInput(
    selectedItem: String,
    items: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) {
    // Corrected: Use rememberBreakpoint for isMobile logic here
    val currentBreakpoint = rememberBreakpoint()
    val isCurrentlyMobile = currentBreakpoint <= Breakpoint.MD

    val finalModifier = Modifier
        .width(80.percent)
        .padding(8.px)
        .border(1.px, LineStyle.Solid, COLOR_INPUT_BACKGROUND)
        .borderRadius(8.px)
        .fontSize(if (isCurrentlyMobile) 14.px else 16.px) // Use correct isCurrentlyMobile
        .backgroundColor(COLOR_INPUT_BACKGROUND)
        .then(modifier)

    Select(
        // Corrected: Use toAttrs with finalHandler and explicit type argument
        attrs = finalModifier.toAttrs(
            finalHandler = {
                onInput { event ->
                    val selectedValue = event.value
                    if (selectedValue != null) {
                        onValueChange(selectedValue)
                    }
                }
            }
        )
    ) {
        // Handle placeholder display correctly
        val showPlaceholder = placeholder != null && (selectedItem.isEmpty() || !items.contains(selectedItem) || selectedItem == placeholder)
        if (showPlaceholder && placeholder != null) {
            Option(value = "", attrs = { selected(); disabled() }) { Text(placeholder) }
        }
        items.forEach { item ->
            Option(value = item, attrs = { if (item == selectedItem && !showPlaceholder) selected() }) {
                Text(item)
            }
        }
    }
}

/**
 * Helper Composable for responsive form rows (label + input).
 */
@Composable
fun ResponsiveFormRow(
    label: String,
    colorLabelText: CSSColorValue,
    labelWidth: Int = 120, // Default width for labels on desktop
    content: @Composable (modifier: Modifier, isMobile: Boolean) -> Unit
) {
    val currentBreakpoint = rememberBreakpoint()
    val isMobile = currentBreakpoint <= Breakpoint.MD

    if (isMobile) {
        Column(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px)) {
            SpanText(label, modifier = Modifier.margin(bottom = 5.px).color(colorLabelText).fontSize(14.px))
            content(Modifier.fillMaxWidth(), true)
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth().margin(bottom = 10.px),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpanText(
                label,
                modifier = Modifier
                    .width(labelWidth.px)
                    .margin(right = 10.px)
                    .color(colorLabelText)
                    .fontSize(16.px)
            )
            content(Modifier.flexGrow(1), false)
        }
    }
}

object ArticleHeaderStyle : StyleSheet() {
    val title by style {
        fontSize(2.8.cssRem)
        lineHeight(1.3.em)
        margin(top = 0.cssRem, bottom = 1.cssRem)
        background(linearGradient(45.deg) {
            stop(Color("#9C7CF4"))
            stop(Color("#6EBAE7"))
        })
        property("-webkit-background-clip", "text")
        property("background-clip", "text")
        property("-webkit-text-fill-color", "transparent")
    }
}

private val COLOR_LABEL_TEXT = Color("#333333")
private val COLOR_INPUT_TEXT = Color("#333333")
private val COLOR_CONTAINER_BACKGROUND = Color("#8EB0E1")
private val COLOR_CONTAINER_SHADOW = Color("#C4787C")
private val COLOR_INNER_CONTAINER_SHADOW = Color("#FFC44F")
private val COLOR_INPUT_BACKGROUND = Color("#8EB0E1")
private val COLOR_INPUT_BORDER = Color("#2D2D5A")


@OptIn(ExperimentalTime::class)
@Page(Redirection.DAILY_WORK_LOG)
@Composable
fun DailyWorkLogPage(ctx: PageContext) {

    Style(ArticleHeaderStyle)
    Style(FooterStyle)

    val workLogs = remember { mutableStateListOf<WorkLogEntry>() }

    var currentTask by remember { mutableStateOf("") }
    var currentStartTime by remember { mutableStateOf("") }
    var currentEndTime by remember { mutableStateOf("") }
    var currentSubject by remember { mutableStateOf("") } // Start with empty for placeholder
    var currentStatus by remember { mutableStateOf("") }   // Start with empty for placeholder
    var currentNotes by remember { mutableStateOf("") }
    var topDate by remember { mutableStateOf("") }

    val currentBreakpoint = rememberBreakpoint()
    val isMobile = currentBreakpoint <= Breakpoint.MD

    Column(
        modifier = Modifier.fillMaxSize().background(COLOR_CONTAINER_BACKGROUND),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(if (isMobile) 10.px else 20.px)
                .maxWidth(700.px)
                .fillMaxWidth()
                .background(COLOR_CONTAINER_BACKGROUND)
                .borderRadius(12.px)
                .boxShadow(0.px, 4.px, 15.px, 0.px, COLOR_CONTAINER_SHADOW)
                .margin(topBottom = 20.px, leftRight = if (isMobile) 5.px else 10.px)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .margin(bottom = 20.px)
                    .thenIf(isMobile) { Modifier.flexDirection(FlexDirection.Column) },
                horizontalArrangement = if (isMobile) Arrangement.Start else Arrangement.SpaceBetween,
                verticalAlignment = if (isMobile) Alignment.Top else Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.thenIf(isMobile) { Modifier.fillMaxWidth().margin(bottom = 10.px) }) {
                    SpanText(
                        "Daily Work Log",
                        modifier = Modifier
                            .fontSize(if (isMobile) 24.px else 32.px)
                            .color(COLOR_LABEL_TEXT)
                            .fontWeight(FontWeight.Bold)
                    )
                    SpanText(
                        "Log your tasks",
                        modifier = Modifier
                            .fontSize(if (isMobile) 16.px else 18.px)
                            .color(COLOR_LABEL_TEXT)
                            .margin(top = 5.px)
                    )
                }
                Column(
                    horizontalAlignment = if (isMobile) Alignment.Start else Alignment.End,
                    modifier = Modifier.thenIf(isMobile) { Modifier.fillMaxWidth().margin(top = 10.px) }
                ) {
                    SpanText("Date:", modifier = Modifier.margin(bottom = 5.px).color(COLOR_LABEL_TEXT).fontSize(14.px))
                    Input(
                        type = InputType.Date,
                        value = topDate,
                        onValueChange = { topDate = it },
                        modifier = Modifier
                            .width(if (isMobile) 100.px else 150.px)
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                            .borderRadius(8.px)
                            .fontSize(if (isMobile) 14.px else 16.px)
                            .backgroundColor(COLOR_INPUT_BACKGROUND)
                            .color(COLOR_INPUT_TEXT)
                    )
                }
            }

            SpanText(
                "Add New Entry",
                modifier = Modifier
                    .fontSize(if (isMobile) 20.px else 24.px)
                    .margin(top = 10.px, bottom = 15.px)
                    .color(COLOR_LABEL_TEXT)
                    .fontWeight(FontWeight.SemiBold)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (isMobile) 15.px else 20.px)
                    .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                    .borderRadius(8.px)
                    .margin(bottom = 20.px)
                    .background(COLOR_CONTAINER_BACKGROUND)
                    .boxShadow(0.px, 2.px, 10.px, 0.px, COLOR_INNER_CONTAINER_SHADOW)
            ) {
                ResponsiveFormRow(label = "Subject:", colorLabelText = COLOR_LABEL_TEXT) { mod, _ ->
                    DropdownInput(
                        selectedItem = currentSubject,
                        items = SUBJECTS,
                        onValueChange = { currentSubject = it },
                        modifier = mod
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                            .backgroundColor(COLOR_INPUT_BACKGROUND)
                            .color(COLOR_INPUT_TEXT),
                        placeholder = "Select Subject"
                    )
                }

                ResponsiveFormRow(label = "Chapter/Section:", colorLabelText = COLOR_LABEL_TEXT) { mod, mobile ->
                    Input(
                        type = InputType.Text,
                        value = currentTask,
                        onValueChange = { currentTask = it },
                        placeholder = "Chapter/Section of work or learning",
                        placeholderColor = PlaceholderColor(COLOR_INPUT_TEXT, opacity = 0.4),
                        modifier = mod
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                            .borderRadius(8.px)
                            .fontSize(if (mobile) 14.px else 16.px)
                            .backgroundColor(COLOR_INPUT_BACKGROUND)
                            .color(COLOR_INPUT_TEXT)
                    )
                }

                ResponsiveFormRow(label = "Task/Topic:", colorLabelText = COLOR_LABEL_TEXT) { mod, mobile ->
                    Input(
                        type = InputType.Text,
                        value = currentTask,
                        onValueChange = { currentTask = it },
                        placeholder = "Topic of work or learning",
                        placeholderColor = PlaceholderColor(COLOR_INPUT_TEXT, opacity = 0.4),
                        modifier = mod
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                            .borderRadius(8.px)
                            .fontSize(if (mobile) 14.px else 16.px)
                            .backgroundColor(COLOR_INPUT_BACKGROUND)
                            .color(COLOR_INPUT_TEXT)
                    )
                }

                if (isMobile) {
                    ResponsiveFormRow(label = "Start Time:", colorLabelText = COLOR_LABEL_TEXT) { mod, mobile ->
                        Input(
                            type = InputType.Time,
                            value = currentStartTime,
                            onValueChange = { currentStartTime = it },
                            modifier = mod
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                .borderRadius(8.px)
                                .fontSize(if (mobile) 14.px else 16.px)
                                .backgroundColor(COLOR_INPUT_BACKGROUND)
                        )
                    }
                    ResponsiveFormRow(label = "End Time:", colorLabelText = COLOR_LABEL_TEXT) { mod, mobile ->
                        Input(
                            type = InputType.Time,
                            value = currentEndTime,
                            onValueChange = { currentEndTime = it },
                            modifier = mod
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                .borderRadius(8.px)
                                .fontSize(if (mobile) 14.px else 16.px)
                                .backgroundColor(COLOR_INPUT_BACKGROUND)
                        )
                    }
                } else {
                    ResponsiveFormRow(label = "Time:", colorLabelText = COLOR_LABEL_TEXT) { mod, _ ->
                        Row(
                            modifier = mod,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.px)
                        ) {
                            Input(
                                type = InputType.Time,
                                value = currentStartTime,
                                onValueChange = { currentStartTime = it },
                                placeholder = "Start",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.px)
                                    .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                    .borderRadius(8.px)
                                    .fontSize(16.px)
                                    .backgroundColor(COLOR_INPUT_BACKGROUND)
                                    .color(COLOR_INPUT_TEXT),
                            )
                            Input(
                                type = InputType.Time,
                                value = currentEndTime,
                                onValueChange = { currentEndTime = it },
                                placeholder = "End",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.px)
                                    .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                    .borderRadius(8.px)
                                    .fontSize(16.px)
                                    .backgroundColor(COLOR_INPUT_BACKGROUND)
                                    .color(COLOR_INPUT_TEXT),
                            )
                        }
                    }
                }

                ResponsiveFormRow(label = "Duration:", colorLabelText = COLOR_LABEL_TEXT) { mod, mobile ->
                    Input(
                        type = InputType.Text,
                        value = currentStartTime,
                        onValueChange = { currentStartTime = it },
                        modifier = mod
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                            .borderRadius(8.px)
                            .fontSize(if (mobile) 14.px else 16.px)
                            .backgroundColor(COLOR_INPUT_BACKGROUND)
                            .color(COLOR_INPUT_TEXT)
                    )
                }

                ResponsiveFormRow(label = "Status:", colorLabelText = COLOR_LABEL_TEXT) { mod, _ ->
                    DropdownInput(
                        selectedItem = currentStatus,
                        items = STATUSES,
                        onValueChange = { currentStatus = it },
                        modifier = mod
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                            .backgroundColor(COLOR_INPUT_BACKGROUND)
                            .color(COLOR_INPUT_TEXT),
                        placeholder = "Select status"
                    )
                }

                ResponsiveFormRow(label = "Description:", colorLabelText = COLOR_LABEL_TEXT) { mod, isNestedMobile ->
                    val textAreaModifier = mod
                        .minHeight(80.px)
                        .padding(8.px)
                        .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                        .borderRadius(8.px)
                        .fontSize(if (isNestedMobile) 14.px else 16.px)
                        .backgroundColor(COLOR_INPUT_BACKGROUND)
                        .color(COLOR_INPUT_TEXT)

                    TextArea( // Corrected: Use toAttrs with finalHandler for TextArea
                        value = currentNotes,

                        attrs = textAreaModifier.toAttrs(
                            finalHandler = {
                                onInput { currentNotes = it.value }
                            }
                        )
                    )
                }

                Button(
                    onClick = {
                        if (topDate.isNotBlank() && currentTask.isNotBlank()) {
                            val newEntry = WorkLogEntry(
                                id = Clock.System.now().toEpochMilliseconds().toString(),
                                date = topDate,
                                task = currentTask,
                                startTime = currentStartTime,
                                endTime = currentEndTime,
                                category = currentSubject.ifEmpty { "N/A" }, // Handle empty selection
                                status = currentStatus.ifEmpty { "N/A" },   // Handle empty selection
                                notes = currentNotes
                            )
                            workLogs.add(0, newEntry)
                            currentTask = ""
                            currentStartTime = ""
                            currentEndTime = ""
                            currentSubject = "" // Reset for placeholder
                            currentStatus = ""   // Reset for placeholder
                            currentNotes = ""
                        } else {
                            println("Date and Task are required fields. Category and Status are recommended.")
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.End)
                        .margin(top = 20.px)
                        .padding(leftRight = 24.px, topBottom = 10.px)
                        .fontSize(if (isMobile) 14.px else 16.px)
                        .borderRadius(8.px)
                ) {
                    Text("Add Entry")
                }
            }

            if (workLogs.isNotEmpty()) {
                Hr(attrs = { style { margin(20.px, 0.px) } })
                SpanText(
                    "Logged Entries (${workLogs.size})",
                    modifier = Modifier
                        .fontSize(if (isMobile) 18.px else 22.px)
                        .margin(bottom = 15.px)
                        .color(Colors.DarkSlateGray)
                        .fontWeight(FontWeight.SemiBold)
                )
                Column(modifier = Modifier.fillMaxWidth()) {
                    workLogs.forEach { entry ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .margin(bottom = 15.px)
                                .padding(15.px)
                                .border(1.px, LineStyle.Solid, Colors.LightSlateGray)
                                .borderRadius(8.px)
                                .background(Colors.White)
                                .boxShadow(0.px, 1.px, 3.px, 0.px, Color("rgba(0,0,0,0.04)"))
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                SpanText(
                                    entry.task,
                                    modifier = Modifier
                                        .weight(1f)
                                        .margin(right = 10.px)
                                        .fontWeight(FontWeight.Bold)
                                        .fontSize(if (isMobile) 16.px else 18.px)
                                )
                                SpanText(
                                    entry.date,
                                    modifier = Modifier
                                        .color(Colors.Gray)
                                        .fontSize(if (isMobile) 12.px else 14.px)
                                )
                            }
                            SpanText("Category: ${entry.category}", modifier = Modifier.margin(top = 6.px).fontSize(if (isMobile) 13.px else 15.px))
                            if (entry.startTime.isNotBlank() || entry.endTime.isNotBlank()) {
                                SpanText("Time: ${entry.startTime.ifBlank { "--:--" }} - ${entry.endTime.ifBlank { "--:--" }}", modifier = Modifier.fontSize(if (isMobile) 13.px else 15.px))
                            }
                            SpanText("Status: ${entry.status}", modifier = Modifier.fontSize(if (isMobile) 13.px else 15.px))
                            if (entry.notes.isNotBlank()) {
                                SpanText(
                                    "Notes:",
                                    modifier = Modifier.margin(top = 6.px).fontWeight(FontWeight.SemiBold).fontSize(if (isMobile) 13.px else 15.px)
                                )
                                SpanText(
                                    entry.notes,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .margin(top = 2.px)
                                        .fontStyle(FontStyle.Italic)
                                        .fontSize(if (isMobile) 13.px else 15.px)
                                        .color(Colors.DarkSlateGray.copyf(alpha = 0.9f))
                                )
                            }
                        }
                    }
                }
            } else {
                SpanText(
                    "No entries logged yet for this session.",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.px)
                        .color(Colors.Gray)
                        .textAlign(TextAlign.Center)
                        .fontSize(if (isMobile) 14.px else 16.px)
                )
            }
        }
    }
}


object FooterStyle : StyleSheet() {
    const val FOOTER_COLOR = "#1A1225"
    const val FOOTER_DARK_COLOR = "#0F0A1A"
    const val FOOTER_ACCENT_START = "#00D4FF"
    const val FOOTER_ACCENT_END = "#FF0080"
    const val FOOTER_LINK_HOVER = "#00D4FF"
    const val FOOTER_HEADING_COLOR = "#ffffff"
    const val FOOTER_TEXT_COLOR = "#cccccc"
    const val FOOTER_LINK_COLOR = "#aaaaaa"

    val footerWrapper by style {
        background(linearGradient(180.deg) {
            stop(Color("#1E1535"))
            stop(Color(FOOTER_COLOR), 30.percent)
            stop(Color("#0F0A1A"), 100.percent)
        })

        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        paddingTop(6.px)
        width(100.percent)
    }

    val topBar by style {
        height(2.px)
        width(100.percent)
        background(linearGradient(90.deg) {
            stop(Color("transparent"))
            stop(Color(FOOTER_ACCENT_START), 20.percent)
            stop(Color(FOOTER_ACCENT_END), 80.percent)
            stop(Color("transparent"))
        })
    }

    val contactSection by style {
        padding(2.cssRem, 0.px)
        color(Color.white)
        background(linearGradient(135.deg) {
            stop(Color("#1A1225"))
            stop(Color("#2A1B3D"), 50.percent)
            stop(Color("#1A1225"), 100.percent)
        })
        borderBottom {
            width(2.px)
            style(LineStyle.Solid)
            color(Color("transparent"))
        }
        property("border-image", "linear-gradient(90deg, $FOOTER_ACCENT_START, $FOOTER_ACCENT_END) 1")
    }

    val contactContainer by style {
        maxWidth(600.px)
        margin(0.px, auto)
        padding(0.px, 2.cssRem)

        media(mediaMaxWidth(AppStyle.mobileFirstBreak)) {
            self {
                padding(0.px, 1.cssRem)
            }
        }
    }

    val contactHeading by style {
        color(Color.white)
        fontSize(1.5.cssRem)
        fontWeight(700)
        textAlign(TextAlign.Center)
        marginTop(0.px)
        marginBottom(1.5.cssRem)
        background(linearGradient(45.deg) {
            stop(Color(FOOTER_ACCENT_START))
            stop(Color(FOOTER_ACCENT_END))
        })
        property("-webkit-background-clip", "text")
        property("-webkit-text-fill-color", "transparent")
        property("-moz-text-fill-color", "transparent")
        property("-moz-background-clip", "text")
    }

    val footer by style {
        color(Color(FOOTER_TEXT_COLOR))
    }

    val footerContent by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        flexWrap(FlexWrap.Wrap)
        justifyContent(JustifyContent.SpaceAround)
        maxWidth(1200.px)
        margin(0.px, auto)
        padding(3.cssRem, 2.cssRem, 2.cssRem)

        media(mediaMaxWidth(AppStyle.mobileFirstBreak)) {
            self {
                flexDirection(FlexDirection.Column)
                gap(2.cssRem)
                padding(2.cssRem, 1.cssRem)
            }
        }
    }

    val footerColumn by style {
        flex("1 1 250px")
        margin(0.px, 1.cssRem, 2.cssRem)
        maxWidth(350.px)

        media(mediaMaxWidth(AppStyle.mobileFirstBreak)) {
            self {
                maxWidth(100.percent)
                margin(0.px)
            }
        }
    }

    val footerHeading by style {
        color(Color(FOOTER_HEADING_COLOR))
        fontSize(1.2.cssRem)
        fontWeight(700)
        marginTop(0.px)
        marginBottom(1.2.cssRem)
        paddingBottom(0.5.cssRem)
        borderBottom {
            width(2.px)
            style(LineStyle.Solid)
            color(Color("transparent"))
        }
        property("border-image", "linear-gradient(90deg, $FOOTER_ACCENT_START, $FOOTER_ACCENT_END) 1")
        property("border-image-slice", "1")
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val footerList by style {
        listStyle("none")
        padding(0.px)
        margin(0.px)

        "li" {
            marginBottom(0.8.cssRem)
        }

        "a" {
            color(Color(FOOTER_LINK_COLOR))
            textDecoration("none")
            transitions {
                defaultDuration(0.3.s)
                properties("color", "text-shadow")
            }

            hover(self) style {
                color(Color(FOOTER_LINK_HOVER))
                textDecoration("underline")
                property("text-shadow", "0 0 8px rgba(0, 212, 255, 0.6)")
            }
        }
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val footerLink by style {
        color(Color(FOOTER_LINK_COLOR))
        textDecoration("none")
        display(DisplayStyle.InlineBlock)
        marginTop(0.8.cssRem)
        transitions {
            defaultDuration(0.3.s)
            properties("color", "text-shadow")
        }

        hover(self) style {
            color(Color(FOOTER_LINK_HOVER))
            textDecoration("underline")
            property("text-shadow", "0 0 8px rgba(0, 212, 255, 0.6)")
        }
    }

    val socialLinks by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        flexWrap(FlexWrap.Wrap)
        gap(2.cssRem)
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val socialIcon by style {
        display(DisplayStyle.InlineBlock)
        fontSize(2.25.cssRem)
        color(Color(FOOTER_LINK_COLOR))
        transitions {
            defaultDuration(0.3.s)
            properties("color", "transform", "text-shadow")
        }

        hover(self) style {
            background(linearGradient(45.deg) {
                stop(Color(FOOTER_ACCENT_START))
                stop(Color(FOOTER_ACCENT_END))
            })
            property("-webkit-background-clip", "text")
            property("-webkit-text-fill-color", "transparent")
            property("transform", "translateY(-3px) scale(1.1)")
            property("text-shadow", "0 0 15px rgba(255, 0, 128, 0.8)")
        }
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val buyMeCoffeeLink by style {
        display(DisplayStyle.Flex)
        alignItems(AlignItems.Center)
        backgroundColor(Color("#ffdd11"))
        color(Color("#000000"))
        padding(0.6.cssRem, 1.cssRem)
        borderRadius(0.5.cssRem)
        marginTop(1.5.cssRem)
        fontWeight(700)
        textDecoration("none")
        width(fitContent)

        transitions {
            defaultDuration(0.3.s)
            properties("background-color", "transform", "box-shadow")
        }

        hover(self) style {
            backgroundColor(Color("#FFEA7F"))
            property("transform", "translateY(-3px)")
            property("box-shadow", "0 4px 8px rgba(0, 0, 0, 0.2)")
        }
    }

    val contactForm by style {
        "button" {
            background(linearGradient(45.deg) {
                stop(Color(FOOTER_ACCENT_START))
                stop(Color(FOOTER_ACCENT_END))
            })
            color(Color.white)
            marginTop(1.cssRem)
            width(100.percent)
            fontWeight(700)
            property("box-shadow", "0 0 15px rgba(0, 212, 255, 0.4)")

            hover {
                property("box-shadow", "0 0 20px rgba(255, 0, 128, 0.6)")
                scale(1.02)
            }
        }
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val footerContactInputs by style {
        display(DisplayStyle.Grid)
        gridTemplateColumns(repeat(2, 1.fr))
        gridTemplateRows {
            repeat(4) {
                size(auto)
            }
        }
        gap(1.cssRem)

        child(self, nthChild(Nth.Functional(1))) style {
            gridArea("1", "1", "2", "2")
        }

        child(self, nthChild(Nth.Functional(2))) style {
            gridArea("1", "2", "2", "3")
        }

        child(self, nthChild(Nth.Functional(3))) style {
            gridArea("2", "1", "3", "3")
        }

        child(self, nthChild(Nth.Functional(4))) style {
            gridArea("3", "1", "5", "3")
        }

        child(self, universal) style {
            size(100.percent)
            minWidth(3.cssRem)

            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            gap(.4.cssRem)

            "label" style {
                color(Color.white)
                fontSize(0.9.cssRem)
            }

            list("input", "textarea") style {
                backgroundColor(Color("#252525"))
                borderRadius(.4.cssRem)
                color(Color("#FFFFFF"))
                border {
                    color(Color("#444444"))
                    style(LineStyle.Solid)
                    width(1.px)
                }
                fontFamily("Open Sans", "sans-serif")
                padding(.5.cssRem)
                transitions {
                    defaultDuration(0.3.s)
                    properties("border-color")
                }

                self + focus style {
                    borderColor(Color.white)
                    outline("none")
                }
            }

            list("input::placeholder", "textarea::placeholder") style {
                color(Color("#FFFFFF7F"))
            }

            "textarea" style {
                height(120.px)
                resize(Resize.None)
            }
        }

        media(mediaMaxWidth(768.px)) {
            self {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
            }
        }
    }

    val footerCopyright by style {
        backgroundColor(Color(FOOTER_DARK_COLOR))
        padding(1.cssRem)
        textAlign("center")
        fontSize(0.9.cssRem)
        color(Color("#888888"))
        borderTop {
            width(1.px)
            style(LineStyle.Solid)
            color(Color("transparent"))
        }
        property("border-image", "linear-gradient(90deg, transparent, $FOOTER_ACCENT_START, $FOOTER_ACCENT_END, transparent) 1")

        "p" {
            margin(0.px)
        }
    }
}
