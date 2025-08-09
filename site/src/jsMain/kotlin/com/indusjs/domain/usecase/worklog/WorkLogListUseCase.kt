package com.indusjs.domain.usecase.worklog

import com.indusjs.domain.model.WorkLogResponse
import com.indusjs.domain.repo.IWorkLogRepository
import com.indusjs.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class WorkLogListUseCase(private val workLogRepository: IWorkLogRepository, dispatcher: CoroutineDispatcher): BaseUseCase<Any, List<WorkLogResponse>>(dispatcher) {
    override suspend fun block(param: Any): List<WorkLogResponse> {
        return workLogRepository.workLogList(param);
    }
}