package com.indusjs.data.repo

import com.indusjs.data.auth.AuthManager
import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.model.WorkLogResponse
import com.indusjs.repository.IWorkLogRepo
import com.indusjs.statustracker.utils.ValidationUtil
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

class WorkLogRepoImpl(private val endPoint: String, private val httpClient: HttpClient): IWorkLogRepo {
    override suspend fun workLogEntry(params: Any): WorkLogResponse {
        val bodyParam = params as? WorkLogBodyParam
        println("WorkLogEntry Request Body: $bodyParam")
        val response: HttpResponse = httpClient.post() {
            url("$endPoint/work-session")
            setBody(bodyParam)
            contentType(ContentType.Application.Json)
            // Add the Authorization header with the Bearer token
            header(HttpHeaders.Authorization, "Bearer ${AuthManager.getCurrentUserToken()}")
        }
        return if(response.status.isSuccess()) {
            response.body<WorkLogResponse>()
        } else {
            throw IllegalArgumentException(response.status.toString())
        }
    }

    override suspend fun workLogList(params: Any): List<WorkLogResponse> {
        val response: HttpResponse = httpClient.get() {
            url("$endPoint/work-session")
            contentType(ContentType.Application.Json)
            // Add the Authorization header with the Bearer token
            header(HttpHeaders.Authorization, "Bearer ${AuthManager.getCurrentUserToken()}")
        }
        return if(response.status.isSuccess()) {
            response.body<List<WorkLogResponse>>()
        } else {
            throw IllegalArgumentException(response.status.toString())
        }
    }
}

@Serializable
data class WorkLogBodyParam(val chapter: String, val duration: String, val start_time: String, val end_time: String,
                            val log_date: String, val notes: String, val subject: String, val subject_id: String,
                            val topic: String, val user_id: String, val status: String)