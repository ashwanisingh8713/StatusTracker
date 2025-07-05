package com.indusjs.repository

import com.indusjs.domain.model.WorkLogResponse

interface IWorkLogRepo {

    fun workLogEntry(params: Any): WorkLogResponse
    fun workLogList(params: Any): List<WorkLogResponse>
}