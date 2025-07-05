package com.indusjs.statustracker.viewmodel

import com.indusjs.domain.usecase.worklog.WorkLogEntryUseCase
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.model.WorkLogEntryState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WorkLogEntryViewModel(private val workLogUseCase: WorkLogEntryUseCase, private val coroutineScope: CoroutineScope) {

    private val _status = MutableStateFlow<WorkLogEntryState>(WorkLogEntryState(workLogEntryResponse = ResourceUiState.Idle))
    val status: StateFlow<WorkLogEntryState> get() = _status

    suspend fun workLogEntry(params: Any) {
        _status.value.copy(workLogEntryResponse = ResourceUiState.Loading)
        coroutineScope.launch {
            workLogUseCase(params)
                .onSuccess { _status.value.copy(workLogEntryResponse = ResourceUiState.Success(it)) }
                .onFailure { _status.value.copy(workLogEntryResponse = ResourceUiState.Error(it.message)) }
        }
    }

}