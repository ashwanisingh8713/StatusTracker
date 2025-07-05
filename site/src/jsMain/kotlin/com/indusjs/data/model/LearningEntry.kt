package com.indusjs.data.model

data class LearningEntry(
    val id: String,
    val subject: String,
    val chapter: String,
    val topic: String,
    val startTime: String,
    val endTime: String,
    val description: String,
    val status: String, // e.g., "Completed", "Pending", "In Progress"
    val duration: String
) {
    /*val duration: Duration
        get() {
            val startInstant = startTime.toInstant(TimeZone.currentSystemDefault())
            val endInstant = endTime.toInstant(TimeZone.currentSystemDefault())
            return endInstant - startInstant
        }

    // Helper to format duration for display
    fun formattedDuration(): String {
        val totalMinutes = duration.inWholeMinutes
        val hours = totalMinutes / 60
        val minutes = totalMinutes % 60
        return "${hours}h ${minutes}m"
    }*/
}