package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.borderColor
import com.varabyte.kobweb.compose.css.borderStyle
import com.varabyte.kobweb.compose.css.borderWidth
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
import org.jetbrains.compose.web.dom.Hr // Still using this one, it's HTML DOM
import org.jetbrains.compose.web.dom.TextArea
import org.jetbrains.compose.web.css.LineStyle // Import LineStyle
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

// Data class to represent a single work log entry
data class WorkLogEntry(
    val id: String, // Unique ID for each entry
    val date: String,
    val task: String,
    val startTime: String,
    val endTime: String,
    val notes: String
)

@Page("/dailylog")
@Composable
fun DailyWorkLogPage() {
    // State to hold all the work log entries
    val workLogs = remember { mutableStateListOf<WorkLogEntry>() }

    // State for the input fields
    var currentTask by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }
    var currentStartTime by remember { mutableStateOf("") }
    var currentEndTime by remember { mutableStateOf("") }
    var currentNotes by remember { mutableStateOf("") }

    Column(
        // Basic styling for the main container: padding, max width, and center it
        // Use leftRight = Auto for explicit horizontal centering with fixed width
        modifier = Modifier.padding(20.px).fillMaxWidth().padding(all = 10.px)
    ) {
        // Page Title - using Silk's Text component, which accepts Modifier
        SpanText("Daily Work Log", modifier = Modifier.fontSize(32.px).margin(bottom = 20.px))

        // --- Input Form Section ---
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.px)
                // Corrected border: width, style, color
                .border(1.px, LineStyle.Solid, Colors.LightGray)
                .borderRadius(8.px)
                .margin(bottom = 20.px) // Add some space below the form
        ) {
            // Date Input
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px), verticalAlignment = Alignment.CenterVertically) {
                SpanText("Date:", modifier = Modifier.width(80.px).margin(right = 10.px))
                Input(
                    type = InputType.Date,
                    value = currentDate,
                    // Corrected onValueChanged to onValueChange
                    onValueChange = { currentDate = it },
                    modifier = Modifier.flexGrow(1)
                )
            }
            // Task Input
            Row(modifier = Modifier.fillMaxWidth().margin(bottom = 10.px), verticalAlignment = Alignment.CenterVertically) {
                SpanText("Task:", modifier = Modifier.width(80.px).margin(right = 10.px))
                Input(
                    value = currentTask,
                    // Corrected onValueChanged to onValueChange
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
                    // Corrected onValueChanged to onValueChange
                    onValueChange = { currentStartTime = it },
                    modifier = Modifier.flexGrow(1).margin(right = 10.px)
                )
                SpanText("End Time:", modifier = Modifier.width(80.px).margin(right = 10.px))
                Input(
                    type = InputType.Time,
                    value = currentEndTime,
                    // Corrected onValueChanged to onValueChange
                    onValueChange = { currentEndTime = it },
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
                        attr("placeholder", "Any additional details...") // Placeholder for textarea
                        style {
                            width(100.percent)
                            minHeight(80.px)
                            padding(8.px)
                            // Corrected border for TextArea attrs
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
                    // Only add if essential fields are not blank
                    if (currentTask.isNotBlank() && currentDate.isNotBlank() && currentStartTime.isNotBlank() && currentEndTime.isNotBlank()) {
                        val newEntry = WorkLogEntry(
                            id = (workLogs.size + 1).toString(), // Simple ID generation (consider UUIDs for real apps)
                            date = currentDate,
                            task = currentTask,
                            startTime = currentStartTime,
                            endTime = currentEndTime,
                            notes = currentNotes
                        )
                        workLogs.add(newEntry)
                        // Clear inputs after adding
                        currentTask = ""
                        currentDate = ""
                        currentStartTime = ""
                        currentEndTime = ""
                        currentNotes = ""
                    }
                },
                modifier = Modifier.alignSelf(AlignSelf.End).padding(top = 10.px)
            ) {
                Text("Add Log Entry")
            }
        }

        //Hr(modifier = Modifier.fillMaxWidth().margin(top = 20.px, bottom = 20.px))
        Hr(
            attrs = {
                style {
                    width(100.percent) // equivalent to fillMaxWidth()
                    marginTop(20.px)
                    marginBottom(20.px)
                    // Optional: style the actual line of the Hr
                    borderWidth(1.px)
                    borderStyle(LineStyle.Solid)
                    borderColor(Color("lightgray")) // or use a specific Hex value, or Colors.LightGray
                    // You might also want to remove default browser Hr styles
                    property("border-top", "none")
                    property("border-left", "none")
                    property("border-right", "none")
                    height(0.px) // Essential to make it just a line
                }
            }
        )

        // --- Display Work Log Entries Section ---
        if (workLogs.isEmpty()) {
            SpanText("No work log entries yet. Add one using the form above!", modifier = Modifier.color(Colors.Gray))
        } else {
            Column {
                // Iterate through the list of work logs and display each one
                workLogs.forEach { entry ->
                    WorkLogEntryCard(entry) { idToDelete ->
                        // Remove the entry when its delete button is clicked
                        //workLogs.removeIf { it.id == idToDelete }
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
            // Corrected border: width, style, color
            .border(1.px, LineStyle.Solid, Colors.LightGray)
            .borderRadius(8.px)
            .background(Colors.AliceBlue) // Light blue background for cards
            .minHeight(80.px),
        verticalAlignment = Alignment.CenterVertically // Vertically align content in the card
    ) {
        Column(modifier = Modifier.flexGrow(1).margin(right = 10.px)) {
            SpanText("Date: ${entry.date}", modifier = Modifier.fontWeight(FontWeight.Bold))
            SpanText("Task: ${entry.task}", modifier = Modifier.margin(top = 5.px))
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
                .backgroundColor(Colors.Red) // Make the delete button red
                .color(Colors.White)
                .borderRadius(4.px)
                .padding(left = 10.px, right = 10.px, top = 5.px, bottom = 5.px)
        ) {
            Text("Delete")
        }
    }
}