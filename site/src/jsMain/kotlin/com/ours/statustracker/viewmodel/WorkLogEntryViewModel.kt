package com.ours.statustracker.viewmodel

import com.indusjs.data.auth.AuthManager
import com.indusjs.data.repo.WorkLogBodyParam
import com.indusjs.domain.usecase.worklog.WorkLogEntryUseCase
import com.ours.statustracker.model.ResourceUiState
import com.ours.statustracker.model.WorkLogEntryState
import com.ours.statustracker.pages.WorkLogEntry
import com.utils.ValidationUtil
import com.utils.ValidationUtil.Companion.formatt
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class WorkLogEntryViewModel(private val workLogUseCase: WorkLogEntryUseCase, private val coroutineScope: CoroutineScope) {

    private val _status = MutableStateFlow<WorkLogEntryState>(WorkLogEntryState(workLogEntryResponse = ResourceUiState.Idle))
    val status: StateFlow<WorkLogEntryState> get() = _status

    val getCoroutineScope: CoroutineScope get() = coroutineScope

    fun sendWorkLogEntry(workLogEntry: WorkLogEntry) {
        _status.value = _status.value.copy(workLogEntryResponse = ResourceUiState.Loading)
        coroutineScope.launch {

            // Here date formatting has to be fixed
            val date = LocalDate.parse(workLogEntry.date)
            val year = date.year
            val month = date.monthNumber
            val day = date.dayOfMonth
            val formattedDate = "${day.formatt()}-${month.formatt()}-${year.formatt()}"
           val formattedVal = workLogEntry.copy(date = formattedDate, startTime = ValidationUtil.convertTo12HourFormat(workLogEntry.startTime),
                endTime = ValidationUtil.convertTo12HourFormat(workLogEntry.endTime))
            println("StartTime ${formattedVal.startTime}")
            val params = WorkLogBodyParam(chapter = formattedVal.chapter, duration = formattedVal.duration.toString(),
                start_time = formattedVal.startTime, end_time = formattedVal.endTime, log_date = formattedVal.date,
                notes = formattedVal.description, subject = formattedVal.subject, subject_id = formattedVal.subjectId,
                topic = formattedVal.task, user_id = AuthManager.getCurrentUserId()!!, status = formattedVal.status)
            workLogUseCase(params)
                .onSuccess { _status.value = _status.value.copy(workLogEntryResponse = ResourceUiState.Success(it)) }
                .onFailure {
                    println("Login Error ${it.cause?.message}")
                    _status.value = _status.value.copy(workLogEntryResponse = ResourceUiState.Error(it.message))
                }
        }
    }
}