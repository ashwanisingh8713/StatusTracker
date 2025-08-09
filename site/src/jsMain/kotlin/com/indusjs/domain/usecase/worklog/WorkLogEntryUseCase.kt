package com.indusjs.domain.usecase.worklog

import com.indusjs.domain.model.WorkLogResponse
import com.indusjs.domain.repo.IWorkLogRepository
import com.indusjs.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class WorkLogEntryUseCase(private val workLogRepository: IWorkLogRepository, dispatcher: CoroutineDispatcher): BaseUseCase<Any, WorkLogResponse>(dispatcher) {

    override suspend fun block(param: Any): WorkLogResponse {
        return workLogRepository.workLogEntry(param)
    }
}