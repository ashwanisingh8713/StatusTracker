package com.ours.statustracker.pages

import androidx.compose.runtime.*
import com.stevdza.san.kotlinbs.components.BSAlert
import com.stevdza.san.kotlinbs.components.BSButton
import com.stevdza.san.kotlinbs.components.BSDropdown
import com.stevdza.san.kotlinbs.forms.BSFileInput
import com.stevdza.san.kotlinbs.forms.BSInput
import com.stevdza.san.kotlinbs.forms.BSTextArea
import com.stevdza.san.kotlinbs.models.AlertStyle
import com.stevdza.san.kotlinbs.models.InputValidation
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toInstant
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Img

@Page("/bt-daily-learning-log-entry")
@Composable
fun BT_DailyLearningLogPage() {
    val breakpoint = rememberBreakpoint()
    var startDate by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var learningLog by remember { mutableStateOf("") }
    var logType by remember { mutableStateOf<String?>(null) }
    var subjectName by remember { mutableStateOf("") }
    var topicCovered by remember { mutableStateOf("") }
    var resourcesUsed by remember { mutableStateOf("") }
    var difficulty by remember { mutableStateOf<String?>(null) }
    var timeSpent by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var attachmentName by remember { mutableStateOf("") }
    var showValidation by remember { mutableStateOf(false) }

    LaunchedEffect(startDate, startTime, endDate, endTime) {
        if (startDate.isNotBlank() && startTime.isNotBlank() && endDate.isNotBlank() && endTime.isNotBlank()) {
            try {
                val startDateTime = LocalDateTime.parse("${startDate}T${startTime}")
                val endDateTime = LocalDateTime.parse("${endDate}T${endTime}")
                if (endDateTime > startDateTime) {
                    val duration = endDateTime.toInstant(kotlinx.datetime.TimeZone.UTC) - startDateTime.toInstant(kotlinx.datetime.TimeZone.UTC)
                    val hours = duration.inWholeMinutes / 60.0
                    timeSpent = (kotlin.math.round(hours * 100) / 100.0).toString()
                } else {
                    timeSpent = ""
                }
            } catch (e: Exception) {
                timeSpent = ""
            }
        } else {
            timeSpent = ""
        }
    }

    val logTypes = listOf("Theory", "Practical", "Project", "Revision")
    val difficultyLevels = listOf("Easy", "Medium", "Hard")

    val isDateValid = startDate.isNotBlank() && endDate.isNotBlank()
    val isTimeValid = startTime.isNotBlank() && endTime.isNotBlank()
    val isStartBeforeEnd = try {
        val start = "${startDate}T${startTime}"
        val end = "${endDate}T${endTime}"
        start < end
    } catch (_: Exception) {
        false
    }
    val isLearningLogValid = learningLog.isNotBlank()
    val isLogTypeValid = logType != null
    val isSubjectValid = subjectName.isNotBlank()
    val isTopicValid = topicCovered.isNotBlank()
    val isDifficultyValid = difficulty != null
    val isTimeSpentValid = timeSpent.toDoubleOrNull()?.let { it > 0 } ?: false

    val isFormValid = isDateValid && isTimeValid && isStartBeforeEnd && isLearningLogValid &&
            isLogTypeValid && isSubjectValid && isTopicValid && isDifficultyValid && isTimeSpentValid

    Box(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(Colors.WhiteSmoke),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(if (breakpoint >= Breakpoint.MD) 800.px else 98.percent)
                .padding(32.px)
                .boxShadow(offsetX = 0.px, offsetY = 4.px, blurRadius = 16.px, color = rgba(0, 0, 0, 0.1))
                .borderRadius(20.px)
                .backgroundColor(Colors.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Img(
                src = "/ASSETS/logo.svg",
                attrs = Modifier.size(150.px).margin(bottom = 24.px).toAttrs()
            )
            SpanText(
                text = "Daily Learning Log Entry",
                modifier = Modifier.fontSize(28.px).fontWeight(FontWeight.Bold).margin(bottom = 32.px)
            )

            Row(modifier = Modifier.gap(16.px).fillMaxWidth()) {
                BSInput(value = startDate, label = "Start Date", type = InputType.Date, onValueChange = { startDate = it }, validation = InputValidation(isInvalid = !isDateValid && showValidation), modifier = Modifier.flex(1))
                BSInput(value = startTime, label = "Start Time", type = InputType.Time, onValueChange = { startTime = it }, validation = InputValidation(isInvalid = !isTimeValid && showValidation), modifier = Modifier.flex(1))
            }
            Row(modifier = Modifier.gap(16.px).fillMaxWidth().margin(top = 12.px)) {
                BSInput(value = endDate, label = "End Date", type = InputType.Date, onValueChange = { endDate = it }, validation = InputValidation(isInvalid = !isDateValid && showValidation), modifier = Modifier.flex(1))
                BSInput(value = endTime, label = "End Time", type = InputType.Time, onValueChange = { endTime = it }, validation = InputValidation(isInvalid = !isTimeValid && showValidation), modifier = Modifier.flex(1))
            }
            if (showValidation && !isStartBeforeEnd) {
                BSAlert(message = "Start date/time must be before end date/time.", style = AlertStyle.Danger, dismissible = true, modifier = Modifier.fillMaxWidth().margin(top = 8.px))
            }

            Row(modifier = Modifier.gap(16.px).fillMaxWidth().margin(top = 20.px)) {
                BSInput(value = subjectName, label = "Subject Name", onValueChange = { subjectName = it }, validation = InputValidation(isInvalid = !isSubjectValid && showValidation), modifier = Modifier.flex(1))
                BSInput(value = topicCovered, label = "Topic Covered", onValueChange = { topicCovered = it }, validation = InputValidation(isInvalid = !isTopicValid && showValidation), modifier = Modifier.flex(1))
            }

            BSTextArea(value = learningLog, label = "Learning Log", onValueChange = { learningLog = it }, validation = InputValidation(isInvalid = !isLearningLogValid && showValidation), modifier = Modifier.fillMaxWidth().margin(top = 20.px))

            Row(modifier = Modifier.gap(16.px).fillMaxWidth().margin(top = 20.px)) {
                BSDropdown(placeholder = "Select Log Type", items = logTypes, onItemSelect = { _, value -> logType = value }, modifier = Modifier.flex(1))
                BSDropdown(placeholder = "Select Difficulty", items = difficultyLevels, onItemSelect = { _, value -> difficulty = value }, modifier = Modifier.flex(1))
            }

            BSInput(value = resourcesUsed, label = "Resources Used", onValueChange = { resourcesUsed = it }, modifier = Modifier.fillMaxWidth().margin(top = 20.px))
            BSInput(
                value = timeSpent,
                label = "Time Spent (in hours)",
                onValueChange = { },
                validation = InputValidation(isInvalid = !isTimeSpentValid && showValidation),
                modifier = Modifier.fillMaxWidth().margin(top = 20.px),
                disabled = true
            )

            BSTextArea(value = notes, label = "Notes", onValueChange = { notes = it }, modifier = Modifier.fillMaxWidth().margin(top = 20.px))

            BSFileInput(label = "Attachments", onFileSelected = { fileName, _ -> attachmentName = fileName }, modifier = Modifier.fillMaxWidth().margin(top = 20.px))
            if (attachmentName.isNotBlank()) {
                SpanText(text = "Attached: $attachmentName", modifier = Modifier.margin(top = 6.px).fontSize(14.px).color(Colors.Gray))
            }

            BSButton(
                text = "Submit",
                onClick = {
                    showValidation = true
                    if(isFormValid) {
                        // Handle submission logic
                        println("Form Submitted Successfully!")
                    }
                 },
                modifier = Modifier.margin(top = 32.px).fillMaxWidth().height(48.px),
                disabled = !isFormValid && showValidation
            )
        }
    }
}
