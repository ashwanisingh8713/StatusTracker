package com.indusjs.domain.repo

import com.indusjs.domain.model.WorkLogResponse

interface IWorkLogRepository {

    fun workLogEntry(params: Any): WorkLogResponse
    fun workLogList(params: Any): List<WorkLogResponse>
}