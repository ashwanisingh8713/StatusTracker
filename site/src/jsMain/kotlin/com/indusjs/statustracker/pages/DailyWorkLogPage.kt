package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.borderColor
import com.varabyte.kobweb.compose.css.borderStyle
import com.varabyte.kobweb.compose.css.borderWidth
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Input
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.Hr
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.border
import org.jetbrains.compose.web.css.borderRadius
import org.jetbrains.compose.web.css.fontSize
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.marginBottom
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.padding
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Select as WasmSelect
import org.jetbrains.compose.web.dom.Option
import org.jetbrains.compose.web.attributes.selected
//import org.jetbrains.compose.web.dom.AttrsScope
//import org.jetbrains.compose.web.dom.toAttrs


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
 * This wraps the low-level org.jetbrains.compose.web.dom.Select.
 */
@Composable
fun DropdownInput(
    selectedItem: String,
    items: List<String>,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) {
    // Define a base Modifier for common dropdown styling.
    val baseModifier = Modifier
        .width(100.percent)
        .padding(8.px)
        .border(1.px, LineStyle.Solid, Colors.LightGray)
        .borderRadius(4.px)
        .fontSize(16.px)

    // Combine the base modifier with the modifier passed to this composable.
    val combinedModifier = baseModifier.then(modifier)

    WasmSelect(
        attrs = {
            // Corrected: Invoke the lambda returned by combinedModifier.toAttrs()
            // This is the standard and correct way to apply Kobweb Modifiers
            // to org.jetbrains.compose.web.dom elements.
            // The `toAttrs()` function returns a lambda (AttrsScope<Element>.() -> Unit)
            // which needs to be invoked within this `attrs` block.
//            combinedModifier.toAttrs().invoke(this)

            onInput { event ->
                val selectedValue = event.value
                if (selectedValue != null) {
                    onValueChange(selectedValue)
                }
            }
        }
    ) {
        if (placeholder != null && !items.contains(selectedItem)) {
            Option(value = "", attrs = { if (selectedItem == "" || selectedItem == placeholder) selected() }) {
                Text(placeholder)
            }
        }
        items.forEach { item ->
            Option(value = item, attrs = { if (item == selectedItem) selected() }) {
                Text(item)
            }
        }
    }
}


@Page("/dailylog")
@Composable
fun DailyWorkLogPage() {
    // State to hold all the work log entries
    val workLogs = remember { mutableStateListOf<WorkLogEntry>() }

    // State for the input fields
    var currentTask by remember { mutableStateOf("") }
    var currentStartTime by remember { mutableStateOf("") }
    var currentEndTime by remember { mutableStateOf("") }
    var currentCategory by remember { mutableStateOf(CATEGORIES.first()) }
    var currentStatus by remember { mutableStateOf(STATUSES.first()) }
    var currentNotes by remember { mutableStateOf("") }

    var topDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(20.px).maxWidth(700.px)//.margin(leftRight = Auto)
    ) {
        // --- Top Row for Title, Subtitle, and Date ---
        Row(
            modifier = Modifier.fillMaxWidth().margin(bottom = 20.px),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                SpanText("Daily Work Log", modifier = Modifier.fontSize(32.px))
                SpanText("Log your tasks", modifier = Modifier.fontSize(18.px).color(Colors.Gray).margin(top = 5.px))
            }
            // Date UI at the right-top corner
            Column(horizontalAlignment = Alignment.End) {
                SpanText("Date:", modifier = Modifier.margin(bottom = 5.px))
                Input(
                    type = InputType.Date,
                    value = topDate,
                    onValueChange = { topDate = it },
                    modifier = Modifier.width(150.px)
                )
            }
        }

        // --- "Add New Entry" Heading ---
        SpanText("Add New Entry", modifier = Modifier.fontSize(24.px).margin(bottom = 15.px))


        // --- Input Form Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.px)
                .border(1.px, LineStyle.Solid, Colors.LightGray)
                .borderRadius(8.px)
                .margin(bottom = 20.px)
        ) {
            // Category Input (moved to top as per request)
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px), verticalAlignment = Alignment.CenterVertically) {
                SpanText("Category:", modifier = Modifier.width(80.px).margin(right = 10.px))
                DropdownInput(
                    selectedItem = currentCategory,
                    items = CATEGORIES,
                    onValueChange = { currentCategory = it },
                    modifier = Modifier.flexGrow(1)
                )
            }
            // Task Input (moved below Category)
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px), verticalAlignment = Alignment.CenterVertically) {
                SpanText("Task:", modifier = Modifier.width(80.px).margin(right = 10.px))
                Input(
                    value = currentTask,
                    onValueChange = { currentTask = it },
                    modifier = Modifier.flexGrow(1),
                    placeholder = "e.g., Code review, Meeting with team",
                    type = InputType.Text
                )
            }
            // Start and End Time Inputs
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px), verticalAlignment = Alignment.CenterVertically) {
                SpanText("Start Time:", modifier = Modifier.width(80.px).margin(right = 10.px))
                Input(
                    type = InputType.Time,
                    value = currentStartTime,
                    onValueChange = { currentStartTime = it },
                    modifier = Modifier.flexGrow(1).margin(right = 10.px)
                )
                SpanText("End Time:", modifier = Modifier.width(80.px).margin(right = 10.px))
                Input(
                    type = InputType.Time,
                    value = currentEndTime,
                    onValueChange = { currentEndTime = it },
                    modifier = Modifier.flexGrow(1)
                )
            }
            // Status of the Task Input (using custom DropdownInput)
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px), verticalAlignment = Alignment.CenterVertically) {
                SpanText("Status:", modifier = Modifier.width(80.px).margin(right = 10.px))
                DropdownInput(
                    selectedItem = currentStatus,
                    items = STATUSES,
                    onValueChange = { currentStatus = it },
                    modifier = Modifier.flexGrow(1)
                )
            }
            // Notes Text Area
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px)) {
                SpanText("Notes:", modifier = Modifier.width(80.px).margin(right = 10.px))
                TextArea(
                    value = currentNotes,
                    attrs = {
                        onInput { currentNotes = it.value }
                        attr("placeholder", "Any additional details...")
                        style {
                            width(100.percent)
                            minHeight(80.px)
                            padding(8.px)
                            border(1.px, LineStyle.Solid, Colors.LightGray)
                            borderRadius(4.px)
                            fontSize(16.px)
                        }
                    }
                )
            }

            // "Add Log Entry" Button
            Button(
                onClick = {
                    if (currentTask.isNotBlank() && topDate.isNotBlank() &&
                        currentStartTime.isNotBlank() && currentEndTime.isNotBlank() &&
                        currentCategory.isNotBlank() && currentStatus.isNotBlank()) {
                        val newEntry = WorkLogEntry(
                            id = (workLogs.size + 1).toString(),
                            date = topDate,
                            task = currentTask,
                            startTime = currentStartTime,
                            endTime = currentEndTime,
                            category = currentCategory,
                            status = currentStatus,
                            notes = currentNotes
                        )
                        workLogs.add(newEntry)
                        currentTask = ""
                        topDate = ""
                        currentStartTime = ""
                        currentEndTime = ""
                        currentCategory = CATEGORIES.first()
                        currentStatus = STATUSES.first()
                        currentNotes = ""
                    }
                },
                modifier = Modifier.alignSelf(AlignSelf.End).padding(top = 10.px)
            ) {
                Text("Add Log Entry")
            }
        }

        Hr(
            attrs = {
                style {
                    width(100.percent)
                    marginTop(20.px)
                    marginBottom(20.px)
                    borderWidth(1.px)
                    borderStyle(LineStyle.Solid)
                    borderColor(Color("lightgray"))
                    property("border-top", "none")
                    property("border-left", "none")
                    property("border-right", "none")
                    height(0.px)
                }
            }
        )

        // --- Display Work Log Entries Section ---
        if (workLogs.isEmpty()) {
            SpanText("No work log entries yet. Add one using the form above!", modifier = Modifier.color(Colors.Gray))
        } else {
            Column {
                workLogs.forEach { entry ->
                    WorkLogEntryCard(entry) { idToDelete ->
                        workLogs.find { it.id == idToDelete }?.let { entryToRemove ->
                            workLogs.remove(entryToRemove)
                        }
                    }
                }
            }
        }
    }
}

/**
 * A reusable Composable function to display a single work log entry.
 */
@Composable
fun WorkLogEntryCard(entry: WorkLogEntry, onDelete: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.px)
            .margin(bottom = 10.px)
            .border(1.px, LineStyle.Solid, Colors.LightGray)
            .borderRadius(8.px)
            .background(Colors.AliceBlue)
            .minHeight(80.px),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.flexGrow(1).margin(right = 10.px)) {
            SpanText("Date: ${entry.date}", modifier = Modifier.fontWeight(FontWeight.Bold))
            SpanText("Task: ${entry.task}", modifier = Modifier.margin(top = 5.px))
            SpanText("Category: ${entry.category}", modifier = Modifier.margin(top = 5.px).fontWeight(FontWeight.Medium))
            SpanText("Status: ${entry.status}", modifier = Modifier.margin(top = 5.px).fontWeight(FontWeight.Medium))
            SpanText("Time: ${entry.startTime} - ${entry.endTime}", modifier = Modifier.margin(top = 5.px))
            if (entry.notes.isNotBlank()) {
                SpanText("Notes: ${entry.notes}", modifier = Modifier.margin(top = 5.px).fontStyle(
                    FontStyle.Italic))
            }
        }
        // Delete button for each entry
        Button(
            onClick = { onDelete(entry.id) },
            modifier = Modifier
                .backgroundColor(Colors.Red)
                .color(Colors.White)
                .borderRadius(4.px)
                .padding(leftRight = 10.px, topBottom = 5.px)
        ) {
            Text("Delete")
        }
    }
}