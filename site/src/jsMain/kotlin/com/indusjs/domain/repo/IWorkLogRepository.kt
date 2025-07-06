package com.indusjs.domain.repo

import com.indusjs.domain.model.WorkLogResponse

interface IWorkLogRepository {

    suspend fun workLogEntry(params: Any): WorkLogResponse
    suspend fun workLogList(params: Any): List<WorkLogResponse>
}