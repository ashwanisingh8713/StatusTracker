package com.indusjs.statustracker.pages

import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.indusjs.data.auth.AuthManager
import com.indusjs.statustracker.AppStyles.COLOR_CONTAINER_BACKGROUND
import com.indusjs.statustracker.AppStyles.COLOR_CONTAINER_SHADOW
import com.indusjs.statustracker.AppStyles.COLOR_INNER_CONTAINER_SHADOW
import com.indusjs.statustracker.AppStyles.COLOR_INPUT_BACKGROUND
import com.indusjs.statustracker.AppStyles.COLOR_INPUT_BORDER
import com.indusjs.statustracker.AppStyles.COLOR_INPUT_TEXT
import com.indusjs.statustracker.AppStyles.COLOR_LABEL_TEXT
import com.indusjs.statustracker.AppStyles.COLOR_OUTPUT_TEXT
import com.indusjs.statustracker.components.MessageAlertDialog
import com.indusjs.statustracker.components.ShowOptionMenu
import com.indusjs.statustracker.components.Toast
import com.indusjs.statustracker.components.VerticalThreeDotMenu
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.utils.ValidationUtil
import com.indusjs.statustracker.viewmodel.WorkLogEntryViewModel
import com.varabyte.kobweb.compose.css.FontStyle
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.TextAlign
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.attributes.selected
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.CSSColorValue
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.FlexDirection
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.*
import org.koin.compose.getKoin
import kotlin.time.ExperimentalTime


// Data class to represent a single work log entry
data class WorkLogEntry(
    val subject: String = "",
    val date: String = "",
    val chapter: String = "",
    val task: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val duration: Int = 0,
    val status: String = "",
    val description: String = "",
    val subjectId : String = "1"

) {
    fun isValid(): Boolean {
        // Check essential fields that are typically mandatory
        if (date.isBlank()) return false
        if (task.isBlank()) return false
        if (startTime.isBlank()) return false
        if (endTime.isBlank()) return false
        if (status.isBlank()) return false
        if (description.isBlank()) return false

        return true // All checked fields have data
    }
}

// Constants for categories and statuses
val SUBJECTS = listOf(
    "Kotlin Fundamental", "GoLang Fundamental", "GoLang Data Structure",
    "Kotlin Data Structure", "Kobweb Fundamental", "Kobweb UI",
    "English Grammer", "Vocabulary"
)

val STATUSES = listOf("In progress", "Done", "Doubt", "ToDo")



@OptIn(ExperimentalTime::class)
@Page(Redirection.DAILY_WORK_LOG)
@Composable
fun DailyWorkLogPage(ctx: PageContext) {

    if(AuthManager.isSignedIn()) {
        println("User is signed in")
    } else {
        ctx.router.navigateTo(Redirection.LOGIN) // Navigate to a protected page
        println("User is not signed in")
    }

    val workLogEntryViewModel = getKoin().get<WorkLogEntryViewModel>()

    val workLogs = remember { mutableStateListOf<WorkLogEntry>() }
    var workLogEntryData by remember { mutableStateOf(WorkLogEntry()) }
    var showToast by remember { mutableStateOf(false) }
    var toastMessage  by remember { mutableStateOf("") }
    var showMessageAlertDialog by remember { mutableStateOf(false) }
    var messageAlertDialog by remember { mutableStateOf("") }

    val addEntryOnClick = {workLogEntry: WorkLogEntry -> workLogEntryViewModel.sendWorkLogEntry(workLogEntry)}

    workLogEntryViewModel.getCoroutineScope.launch {
        workLogEntryViewModel.status.collectLatest {
            when(it.workLogEntryResponse) {
                is ResourceUiState.Success -> {
                    toastMessage = "Your work log entry has been saved successfully."
                    showToast = true
                    workLogEntryData = WorkLogEntry()
                    //ctx.router.navigateTo(Redirection.DAILY_WORK_LOG) // Navigate to a protected page
                    println("Login Success")
                }
                is ResourceUiState.Error -> {
                    messageAlertDialog = "Error! "+it.workLogEntryResponse.message!!
                    showMessageAlertDialog = true
                    println("Login Error ${it.workLogEntryResponse.message}")
                }
                is ResourceUiState.Idle -> {
                    println("Login Idle")
                }
                is ResourceUiState.Empty -> {
                    println("Login Empty")
                }
                is ResourceUiState.Loading -> {
                    println("Login Loading")
                }
            }
        }
    }

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
                    Row() {
                        SpanText(
                            "Work Log Entry",
                            modifier = Modifier
                                .fontSize(if (isMobile) 24.px else 32.px)
                                .color(COLOR_LABEL_TEXT)
                                .fontWeight(FontWeight.Bold)
                        )

                        if(isMobile) {
                            ShowOptionMenu(false, ctx, Modifier
                                .width(100.px)
                                .alignItems(AlignItems.End)
                                .margin(left = 100.px)
                                .padding(leftRight = 10.px, topBottom = 10.px)
                                .fontSize(12.px)
                                .borderRadius(8.px))
                        }
                    }
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
                    Row {

                        SpanText(
                            "Date:",
                            modifier = Modifier.margin(bottom = 5.px).color(COLOR_LABEL_TEXT).padding(right = 8.px)
                                .fontSize(14.px).align(alignment = Alignment.CenterVertically)
                        )
                        Input(
                            type = InputType.Date,
                            value = workLogEntryData.date,
                            onValueChange = { newDateValue ->
                                workLogEntryData = workLogEntryData.copy(date = newDateValue)
                            },

                            modifier = Modifier
                                .width(if (isMobile) 150.px else 150.px)
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                .borderRadius(8.px)
                                .fontSize(if (isMobile) 14.px else 16.px)
                                .backgroundColor(COLOR_INPUT_BACKGROUND)
                                .color(COLOR_INPUT_TEXT)
                        )
                    }
                }
                if(!isMobile) {
                    ShowOptionMenu(false, ctx, Modifier.position(Position.Relative))
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
                        selectedItem = workLogEntryData.subject,
                        items = SUBJECTS,
                        onValueChange = { subject -> workLogEntryData = workLogEntryData.copy(subject= subject) },
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
                        value = workLogEntryData.chapter,
                        onValueChange = { chapter -> workLogEntryData = workLogEntryData.copy(chapter = chapter)},
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
                        value = workLogEntryData.task,
                        onValueChange = { task -> workLogEntryData = workLogEntryData.copy(task = task) },
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
                            value = workLogEntryData.startTime,
                            onValueChange = { startTime -> workLogEntryData = workLogEntryData.copy(startTime = startTime) },
                            modifier = mod
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                .borderRadius(8.px)
                                .fontSize(if (mobile) 14.px else 16.px)
                                .backgroundColor(COLOR_INPUT_BACKGROUND)
                                .color(COLOR_INPUT_TEXT)
                        )
                    }
                    ResponsiveFormRow(label = "End Time:", colorLabelText = COLOR_LABEL_TEXT) { mod, mobile ->
                        Input(
                            type = InputType.Time,
                            value = workLogEntryData.endTime,
                            onValueChange = { endTime -> workLogEntryData = workLogEntryData.copy(endTime = endTime) },
                            modifier = mod
                                .padding(8.px)
                                .border(1.px, LineStyle.Solid, COLOR_INPUT_BORDER)
                                .borderRadius(8.px)
                                .fontSize(if (mobile) 14.px else 16.px)
                                .backgroundColor(COLOR_INPUT_BACKGROUND)
                                .color(COLOR_INPUT_TEXT)
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
                                value = workLogEntryData.startTime,
                                onValueChange = { startTime -> println("Ashwani Time ${ValidationUtil.convertTo12HourFormat(startTime)}")
                                    workLogEntryData = workLogEntryData.copy(startTime = startTime) },
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
                                value = workLogEntryData.endTime,
                                onValueChange = { endTime -> workLogEntryData = workLogEntryData.copy(endTime = endTime)},
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
                        type = InputType.Number,
                        value = workLogEntryData.duration,
                        onValueChange = { duration -> workLogEntryData = workLogEntryData.copy(duration = duration as Int)  },
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
                        selectedItem = workLogEntryData.status,
                        items = STATUSES,
                        onValueChange = { status -> workLogEntryData = workLogEntryData.copy(status = status) },
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
                        value = workLogEntryData.description,
                        attrs = textAreaModifier.toAttrs(
                            finalHandler = {
                                onInput { description -> workLogEntryData = workLogEntryData.copy(description = description.value) }
                            }
                        )
                    )
                }

                Row {
                    Button(
                        onClick = {
                            if (workLogEntryData.isValid()) {
                                workLogs.add(workLogEntryData)
                                // TODO, make api request to log entry in Server
                            } else {
                                println("Date and Task are required fields. Category and Status are recommended.")

                            }
                        },
                        modifier = Modifier
                            .alignItems(AlignItems.FlexEnd)
                            .margin(top = 20.px)
                            .padding(leftRight = 24.px, topBottom = 10.px)
                            .fontSize(if (isMobile) 14.px else 16.px)
                            .borderRadius(8.px)
                    ) {
                        Text("Preview")
                    }

                    com.indusjs.statustracker.utils.Spacer(modifier = Modifier.width(40.px))

                    Button(
                        onClick = {
                            if (workLogEntryData.isValid()) {
                                addEntryOnClick(workLogEntryData)
                            } else {
                                println("Date and Task are required fields. Category and Status are recommended.")
                                messageAlertDialog = "Date and Task are required fields. Category and Status are recommended."
                                showMessageAlertDialog = true
                            }
                        },
                        modifier = Modifier
                            .alignItems(AlignItems.FlexEnd)
                            .margin(top = 20.px)
                            .padding(leftRight = 24.px, topBottom = 10.px)
                            .fontSize(if (isMobile) 14.px else 16.px)
                            .borderRadius(8.px)
                    ) {
                        Text("Add Entry")
                    }
                }
            }

            // Preview UIs
            if (workLogs.isNotEmpty()) {
                Hr(attrs = { style { margin(20.px, 0.px) } })
                SpanText(
                    "Preview of entered work log",
                    modifier = Modifier
                        .fontSize(if (isMobile) 18.px else 22.px)
                        .margin(bottom = 15.px)
                        .color(COLOR_INPUT_TEXT)
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
                                .background(COLOR_INPUT_TEXT)
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
                                    "Date: "+entry.date,
                                    modifier = Modifier
                                        .color(COLOR_OUTPUT_TEXT)
                                        .fontSize(if (isMobile) 12.px else 14.px)
                                )
                            }
                            SpanText("Subject: ${entry.subject}", modifier = Modifier.margin(top = 6.px).color(COLOR_OUTPUT_TEXT).fontSize(if (isMobile) 13.px else 15.px))
                            if (entry.startTime.isNotBlank() || entry.endTime.isNotBlank()) {
                                SpanText("Time: ${entry.startTime.ifBlank { "--:--" }} - ${entry.endTime.ifBlank { "--:--" }}", modifier = Modifier.color(COLOR_OUTPUT_TEXT).fontSize(if (isMobile) 13.px else 15.px))
                            }
                            SpanText("Status: ${entry.status}", modifier = Modifier.color(COLOR_OUTPUT_TEXT).fontSize(if (isMobile) 13.px else 15.px))
                            if (entry.description.isNotBlank()) {
                                SpanText(
                                    "Notes:",
                                    modifier = Modifier.margin(top = 6.px).color(COLOR_OUTPUT_TEXT).fontWeight(FontWeight.SemiBold).fontSize(if (isMobile) 13.px else 15.px)
                                )
                                SpanText(
                                    entry.description,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .margin(top = 2.px)
                                        .fontStyle(FontStyle.Italic)
                                        .fontSize(if (isMobile) 13.px else 15.px)
                                        .color(COLOR_OUTPUT_TEXT)
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



    // Show Alert Dialog Message
    if (showMessageAlertDialog) {
        MessageAlertDialog(
            message = messageAlertDialog,
            onOk = { showMessageAlertDialog = false },
            onCancel = { showMessageAlertDialog = false }
        )
    }

    if(showToast) {
        Toast(toastMessage, isMobile,showToast) { showToast = false }
    }
}



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

