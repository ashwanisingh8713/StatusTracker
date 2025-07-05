package com.indusjs.statustracker.viewmodel

import com.indusjs.domain.usecase.worklog.WorkLogListUseCase
import kotlinx.coroutines.CoroutineScope

class WorkLogListViewModel(private val workLogListUseCase: WorkLogListUseCase, private val coroutineScope: CoroutineScope) {
}