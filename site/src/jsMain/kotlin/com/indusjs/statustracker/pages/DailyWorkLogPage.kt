package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
import com.varabyte.kobweb.compose.css.margin
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
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.placeholder // For TextArea placeholder & Input
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.HTMLTextAreaElement
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
val CATEGORIES = listOf(
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
        .width(100.percent)
        .padding(8.px)
        .border(1.px, LineStyle.Solid, Colors.LightGray)
        .borderRadius(8.px)
        .fontSize(if (isCurrentlyMobile) 14.px else 16.px) // Use correct isCurrentlyMobile
        .backgroundColor(Colors.White)
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
    labelWidth: Int = 90, // Default width for labels on desktop
    content: @Composable (modifier: Modifier, isMobile: Boolean) -> Unit
) {
    val currentBreakpoint = rememberBreakpoint()
    val isMobile = currentBreakpoint <= Breakpoint.MD

    if (isMobile) {
        Column(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px)) {
            SpanText(label, modifier = Modifier.margin(bottom = 5.px).color(Colors.DarkGray).fontSize(14.px))
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
                    .color(Colors.DarkGray)
                    .fontSize(16.px)
            )
            content(Modifier.flexGrow(1), false)
        }
    }
}


@OptIn(ExperimentalTime::class)
@Page("/dailylog")
@Composable
fun DailyWorkLogPage() {

    val workLogs = remember { mutableStateListOf<WorkLogEntry>() }

    var currentTask by remember { mutableStateOf("") }
    var currentStartTime by remember { mutableStateOf("") }
    var currentEndTime by remember { mutableStateOf("") }
    var currentCategory by remember { mutableStateOf("") } // Start with empty for placeholder
    var currentStatus by remember { mutableStateOf("") }   // Start with empty for placeholder
    var currentNotes by remember { mutableStateOf("") }
    var topDate by remember { mutableStateOf("") }

    val currentBreakpoint = rememberBreakpoint()
    val isMobile = currentBreakpoint <= Breakpoint.MD

    Column(
        modifier = Modifier.fillMaxSize().background(Color("#e0e8f0")),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(if (isMobile) 10.px else 20.px)
                .maxWidth(700.px)
                .fillMaxWidth()
                .background(Color("#f7f9fc"))
                .borderRadius(12.px)
                .boxShadow(0.px, 4.px, 15.px, 0.px, Color("rgba(0, 0, 0, 0.05)"))
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
                            .color(Colors.DarkSlateBlue)
                            .fontWeight(FontWeight.Bold)
                    )
                    SpanText(
                        "Log your tasks",
                        modifier = Modifier
                            .fontSize(if (isMobile) 16.px else 18.px)
                            .color(Colors.Gray)
                            .margin(top = 5.px)
                    )
                }
                Column(
                    horizontalAlignment = if (isMobile) Alignment.Start else Alignment.End,
                    modifier = Modifier.thenIf(isMobile) { Modifier.fillMaxWidth().margin(top = 10.px) }
                ) {
                    SpanText("Date:", modifier = Modifier.margin(bottom = 5.px).color(Colors.DarkGray).fontSize(14.px))
                    Input(
                        type = InputType.Date,
                        value = topDate,
                        onValueChange = { topDate = it },
                        modifier = Modifier
                            .width(if (isMobile) 100.px else 150.px)
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, Colors.LightGray)
                            .borderRadius(8.px)
                            .fontSize(if (isMobile) 14.px else 16.px)
                            .backgroundColor(Colors.White)
                    )
                }
            }

            SpanText(
                "Add New Entry",
                modifier = Modifier
                    .fontSize(if (isMobile) 20.px else 24.px)
                    .margin(top = 10.px, bottom = 15.px)
                    .color(Colors.DarkSlateGray)
                    .fontWeight(FontWeight.SemiBold)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if (isMobile) 15.px else 20.px)
                    .border(1.px, LineStyle.Solid, Colors.LightGray)
                    .borderRadius(8.px)
                    .margin(bottom = 20.px)
                    .background(Colors.White)
                    .boxShadow(0.px, 2.px, 10.px, 0.px, Color("rgba(0, 0, 0, 0.03)"))
            ) {
                ResponsiveFormRow(label = "Category:") { mod, _ ->
                    DropdownInput(
                        selectedItem = currentCategory,
                        items = CATEGORIES,
                        onValueChange = { currentCategory = it },
                        modifier = mod,
                        placeholder = "Select category"
                    )
                }
                ResponsiveFormRow(label = "Task:") { mod, mobile ->
                    Input(
                        type = InputType.Text,
                        value = currentTask,
                        onValueChange = { currentTask = it },
                        placeholder = "e.g., Code review, Meeting with team",
                        modifier = mod
                            .padding(8.px)
                            .border(1.px, LineStyle.Solid, Colors.LightGray)
                            .borderRadius(8.px)
                            .fontSize(if (mobile) 14.px else 16.px)
                            .backgroundColor(Colors.White)
                    )
                }

                if (isMobile) {
                    ResponsiveFormRow(label = "Start Time:") { mod, mobile ->
                        Input(
                            type = InputType.Time,
                            value = currentStartTime,
                            onValueChange = { currentStartTime = it },
                            modifier = mod
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, Colors.LightGray)
                                .borderRadius(8.px)
                                .fontSize(if (mobile) 14.px else 16.px)
                                .backgroundColor(Colors.White)
                        )
                    }
                    ResponsiveFormRow(label = "End Time:") { mod, mobile ->
                        Input(
                            type = InputType.Time,
                            value = currentEndTime,
                            onValueChange = { currentEndTime = it },
                            modifier = mod
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, Colors.LightGray)
                                .borderRadius(8.px)
                                .fontSize(if (mobile) 14.px else 16.px)
                                .backgroundColor(Colors.White)
                        )
                    }
                } else {
                    ResponsiveFormRow(label = "Time:") { mod, _ ->
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
                                    .border(1.px, LineStyle.Solid, Colors.LightGray)
                                    .borderRadius(8.px)
                                    .fontSize(16.px)
                                    .backgroundColor(Colors.White)
                            )
                            Input(
                                type = InputType.Time,
                                value = currentEndTime,
                                onValueChange = { currentEndTime = it },
                                placeholder = "End",
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(8.px)
                                    .border(1.px, LineStyle.Solid, Colors.LightGray)
                                    .borderRadius(8.px)
                                    .fontSize(16.px)
                                    .backgroundColor(Colors.White)
                            )
                        }
                    }
                }

                ResponsiveFormRow(label = "Status:") { mod, _ ->
                    DropdownInput(
                        selectedItem = currentStatus,
                        items = STATUSES,
                        onValueChange = { currentStatus = it },
                        modifier = mod,
                        placeholder = "Select status"
                    )
                }

                ResponsiveFormRow(label = "Notes:") { mod, isNestedMobile ->
                    val textAreaModifier = mod
                        .minHeight(80.px)
                        .padding(8.px)
                        .border(1.px, LineStyle.Solid, Colors.LightGray)
                        .borderRadius(8.px)
                        .fontSize(if (isNestedMobile) 14.px else 16.px)
                        .backgroundColor(Colors.White)

                    TextArea( // Corrected: Use toAttrs with finalHandler for TextArea
                        value = currentNotes,
                        attrs = textAreaModifier.toAttrs(
                            finalHandler = {
                                onInput { currentNotes = it.value }
                                placeholder("Add any relevant notes or details...")
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
                                category = currentCategory.ifEmpty { "N/A" }, // Handle empty selection
                                status = currentStatus.ifEmpty { "N/A" },   // Handle empty selection
                                notes = currentNotes
                            )
                            workLogs.add(0, newEntry)
                            currentTask = ""
                            currentStartTime = ""
                            currentEndTime = ""
                            currentCategory = "" // Reset for placeholder
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