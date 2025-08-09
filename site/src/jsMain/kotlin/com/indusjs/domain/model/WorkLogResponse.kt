package com.indusjs.domain.model

import kotlinx.serialization.Serializable

//data class WorkLogResponse(val success: Boolean, val message: String)

@Serializable
data class WorkLogResponse(
    val chapter: String,
    val duration: Int,
    val end_time: String,
    val id: Int = -1,
    val log_date: String,
    val notes: String,
    val start_time: String,
    val subject: String,
    val subject_id: Int,
    val topic: String,
    val user_id: Int,
    val status: String? = null
)