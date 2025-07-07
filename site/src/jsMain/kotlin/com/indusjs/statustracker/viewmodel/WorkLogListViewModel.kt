package com.indusjs.statustracker.viewmodel

import com.indusjs.domain.usecase.worklog.WorkLogListUseCase
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.model.WorkLogListState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkLogListViewModel(private val workLogListUseCase: WorkLogListUseCase, private val coroutineScope: CoroutineScope) {

    private val _status = MutableStateFlow<WorkLogListState>(WorkLogListState(workLogListResponse = ResourceUiState.Idle))
    val status: MutableStateFlow<WorkLogListState> = _status

    val getCoroutineScope: CoroutineScope get() = coroutineScope

    fun sendWorkLogListRequest() {
        _status.value = _status.value.copy(workLogListResponse = ResourceUiState.Loading)
        coroutineScope.launch {
            workLogListUseCase("")
                .onSuccess { _status.value = _status.value.copy(workLogListResponse = ResourceUiState.Success(it)) }
                .onFailure {
                    println("WorkLogListViewModel Error ${it.cause?.message}")
                    _status.value = _status.value.copy(workLogListResponse = ResourceUiState.Error(it.message))
                }

        }

    }


}