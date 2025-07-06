package com.indusjs.statustracker.viewmodel

import com.indusjs.data.repo.WorkLogBodyParam
import com.indusjs.domain.usecase.worklog.WorkLogEntryUseCase
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.model.WorkLogEntryState
import com.indusjs.statustracker.pages.WorkLogEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkLogEntryViewModel(private val workLogUseCase: WorkLogEntryUseCase, private val coroutineScope: CoroutineScope) {

    private val _status = MutableStateFlow<WorkLogEntryState>(WorkLogEntryState(workLogEntryResponse = ResourceUiState.Idle))
    val status: StateFlow<WorkLogEntryState> get() = _status

    val getCoroutineScope: CoroutineScope get() = coroutineScope

    fun sendWorkLogEntry(workLogEntry: WorkLogEntry) {
        _status.value = _status.value.copy(workLogEntryResponse = ResourceUiState.Loading)
        coroutineScope.launch {
            val params = WorkLogBodyParam(chapter = workLogEntry.chapter, duration = 1,
                start_time = workLogEntry.startTime, end_time = workLogEntry.endTime, log_date = workLogEntry.date,
                notes = workLogEntry.description, subject = workLogEntry.subject, subject_id = 1, topic = workLogEntry.task, user_id = 1)
            workLogUseCase(params)
                .onSuccess { _status.value = _status.value.copy(workLogEntryResponse = ResourceUiState.Success(it)) }
                .onFailure {
                    println("Login Error ${it.cause?.message}")
                    _status.value = _status.value.copy(workLogEntryResponse = ResourceUiState.Error(it.message))
                }
        }
    }
}