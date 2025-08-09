package com.indusjs.repository

import com.indusjs.domain.model.WorkLogResponse

interface IWorkLogRepo {

    suspend fun workLogEntry(params: Any): WorkLogResponse
    suspend fun workLogList(params: Any): List<WorkLogResponse>
}