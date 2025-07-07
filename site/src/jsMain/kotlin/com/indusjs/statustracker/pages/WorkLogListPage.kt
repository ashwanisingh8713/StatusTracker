package com.indusjs.statustracker.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.indusjs.data.auth.AuthManager
import com.indusjs.domain.model.WorkLogResponse
import com.indusjs.statustracker.AppStyles
import com.indusjs.statustracker.components.ShowOptionMenu
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.utils.Redirection
import com.indusjs.statustracker.viewmodel.WorkLogListViewModel
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.AlignItems
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.div
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.koin.compose.getKoin


@Page(Redirection.DASHBOARD)
@Composable
fun WorkLogListPage(ctx: PageContext) {
    if(AuthManager.isSignedIn()) {
        println("User is signed in")
    } else {
        ctx.router.navigateTo(Redirection.LOGIN) // Navigate to a protected page
        println("User is not signed in")
    }

    val learningEntries = remember { mutableStateListOf<WorkLogResponse>() }

    val workLogListViewModel = getKoin().get<WorkLogListViewModel>()
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        workLogListViewModel.sendWorkLogListRequest()
    }

    workLogListViewModel.getCoroutineScope.launch {
        workLogListViewModel.status.collectLatest {
            when(it.workLogListResponse) {
                is ResourceUiState.Success -> {
                    val dd = it.workLogListResponse.data
                    learningEntries.addAll(dd)
                    //ctx.router.navigateTo(Redirection.DAILY_WORK_LOG) // Navigate to a protected page
                    println("Login Success")
                }
                is ResourceUiState.Error -> {

                }
                is ResourceUiState.Idle -> {
                    isLoading = false
                    println("Login Idle")
                }
                is ResourceUiState.Empty -> {
                    println("Login Empty")
                    isLoading = false
                }
                is ResourceUiState.Loading -> {
                    println("Login Loading")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .backgroundColor(AppStyles.BackgroundColor)
            .padding(left = AppStyles.MarginDefault, right = AppStyles.MarginDefault),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            SpanText(
                "Work Log List",
                modifier = Modifier
                    .alignItems(AlignItems.Start)
                    .fontSize(AppStyles.FontSizeLarge)
                    .color(AppStyles.COLOR_LABEL_TEXT)
                    .padding(leftRight = 10.px, topBottom = 10.px)
                //.margin(bottom = AppStyles.PaddingDefault * 2)
            )
            ShowOptionMenu(true, ctx, Modifier
                .alignItems(AlignItems.End)
                .margin(left = 100.px)
                .padding(leftRight = 10.px, topBottom = 5.px)
                .fontSize(12.px)
                .borderRadius(8.px))
        }

        Column(
            modifier = Modifier
                .fillMaxWidth() // Constrain width for better readability on large screens
                .maxWidth(1200.px) // Max width for content
        ) {
            if (learningEntries.isEmpty()) {
                SpanText("No learning entries yet!", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor))
            } else if(isLoading) {
                SpanText("Loading...", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor))
            }
            else {
                learningEntries.forEach { entry ->
                    LearningEntryRow(entry)
                }
            }
        }
    }
}

@Composable
fun LearningEntryRow(entry: WorkLogResponse, modifier: Modifier = Modifier) {
    val currentBreakpoint = rememberBreakpoint()
    val isMobile = currentBreakpoint <= Breakpoint.MD

    // Conditionally render based on window width
    if (isMobile) {
        LearningEntryRowMobile(entry, modifier)
    } else {
        LearningEntryRowDesktop(entry, modifier)
    }
}

// Mobile version of the LearningEntryRow
@Composable
fun LearningEntryRowMobile(entry: WorkLogResponse, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(AppStyles.PaddingDefault)
            .margin(bottom = AppStyles.MarginDefault)
            .borderRadius(AppStyles.BorderRadiusDefault)
            .backgroundColor(Colors.White)
            .border(1.px, LineStyle.Groove),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(AppStyles.MarginDefault / 2) // Small spacing between stacked items
    ) {
        // Main details (Subject, Chapter, Topic)
        Column {
            SpanText("Subject: ${entry.subject}", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor).fontWeight(com.varabyte.kobweb.compose.css.FontWeight.Bold))
            SpanText("Chapter: ${entry.chapter}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
            SpanText("Topic: ${entry.topic}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
        }

        // Time and Duration on one row for better compactness
        Row(
            modifier = Modifier.fillMaxWidth().margin(top = AppStyles.MarginDefault / 2),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                SpanText("Start: ${entry.start_time}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
                SpanText("End: ${entry.end_time}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
            }
            SpanText("Duration: ${entry.duration}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.SecondaryColor).fontWeight(com.varabyte.kobweb.compose.css.FontWeight.Bold))
        }

        // Status and Description
        val statusColor = when (entry.status) {
            "Completed" -> AppStyles.SuccessColor
            "Pending" -> AppStyles.PendingColor
            "In Progress" -> AppStyles.CompletedColor
            else -> AppStyles.TextColor
        }
        SpanText("Status: ${entry.status}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(statusColor).fontWeight(com.varabyte.kobweb.compose.css.FontWeight.Bold).margin(top = AppStyles.MarginDefault / 2))
        SpanText("Description: ${entry.notes}",
            modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor).margin(top = AppStyles.MarginDefault / 4))
    }
}
@Composable
fun LearningEntryRowDesktop(entry: WorkLogResponse, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(left=AppStyles.MarginDefault)
            .margin(bottom = AppStyles.MarginDefault)
            .borderRadius(AppStyles.BorderRadiusDefault)
            .backgroundColor(Colors.White)
            .border(1.px, LineStyle.Groove),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Left section: Subject, Chapter, Topic
        Column(modifier = Modifier
            .flexGrow(1)
            .padding(right = AppStyles.MarginDefault)) {
            SpanText("Subject: ${entry.subject}", modifier = Modifier.fontSize(AppStyles.FontSizeMedium).color(AppStyles.TextColor))
            SpanText("Chapter: ${entry.chapter}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
            SpanText("Topic: ${entry.topic}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(AppStyles.TextColor))
        }

        // Middle section: Time and Duration
        Column(modifier = Modifier
            .flexGrow(1)
            .padding(right = AppStyles.MarginDefault)) {
            SpanText("Start: ${entry.start_time}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
            SpanText("End: ${entry.end_time}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
            SpanText("Duration: ${entry.duration}", modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
        }

        // Right section: Status and Description (can be a summary or full text)
        Column(modifier = Modifier.flexGrow(1f)) {
            val statusColor = when (entry.status) {
                "Completed" -> AppStyles.SuccessColor
                "Pending" -> AppStyles.PendingColor
                "In Progress" -> AppStyles.CompletedColor // Using a different blue for in progress
                else -> AppStyles.TextColor
            }
            SpanText("Status: ${entry.status}", modifier = Modifier.fontSize(AppStyles.FontSizeNormal).color(statusColor))
            SpanText("Description: ${entry.notes.take(50)}...", // Truncate for brevity in list
                modifier = Modifier.fontSize(AppStyles.FontSizeSmall).color(AppStyles.TextColor))
        }
    }
}


