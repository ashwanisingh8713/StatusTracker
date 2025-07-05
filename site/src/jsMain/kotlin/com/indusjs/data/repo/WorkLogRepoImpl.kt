package com.indusjs.data.repo

import com.indusjs.domain.model.WorkLogResponse
import com.indusjs.repository.IWorkLogRepo
import io.ktor.client.HttpClient

class WorkLogRepoImpl(private val endPoint: String, private val client: HttpClient): IWorkLogRepo {
    override fun workLogEntry(params: Any): WorkLogResponse {
        TODO("Not yet implemented")
    }

    override fun workLogList(params: Any): List<WorkLogResponse> {
        TODO("Not yet implemented")
    }
}


data class WorkLogBodyParam(val chapter: String, val duration: Int, val start_time: String,
                       val end_time: String, val log_date: String, val notes: String,
    val subject: Int, val subject_id: Int, val topic: String, val user_id: Int)