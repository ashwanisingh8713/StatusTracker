package com.indusjs.repository

import com.indusjs.domain.model.WorkLogResponse
import com.indusjs.domain.repo.IWorkLogRepository

class WorkLogRepositoryImpl(private val workLogRepo: IWorkLogRepo): IWorkLogRepository {

    override fun workLogEntry(params: Any): WorkLogResponse {
        return workLogRepo.workLogEntry(params)
    }

    override fun workLogList(params: Any): List<WorkLogResponse> {
        return workLogRepo.workLogList(params)
    }
}