package com.indusjs.statustracker.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.internal.JSJoda.DateTimeFormatter
import kotlinx.datetime.internal.JSJoda.DateTimeParseException
import kotlinx.datetime.internal.JSJoda.Duration
import kotlinx.datetime.internal.JSJoda.LocalTime

import kotlinx.datetime.internal.JSJoda.Locale

class ValidationUtil {
    companion object {
        // To validate email
        fun validateEmail(email: String): String {
            if (email.isEmpty()) {
                return "Email cannot be empty."
            }
            val atIndex = email.indexOf('@')
            val dotIndex = email.lastIndexOf('.')

            if (atIndex == -1 || dotIndex == -1 || atIndex > dotIndex || atIndex == 0 || dotIndex == email.length - 1) {
                return "Invalid email format."
            }
            if (email.contains(" ")) {
                return "Email cannot contain spaces."
            }
            return ""
        }

        // To validate Password
        fun validatePassword(password: String): String {
            if (password.isEmpty()) {
                return "Password cannot be empty."
            }
            if (password.length < 5) {
                return "Password must be at least 8 characters long."
            }

            var hasUppercase = true
            var hasLowercase = true
            var hasDigit = true
            var hasSpecialChar = true

            /*var hasUppercase = false
    var hasLowercase = false
    var hasDigit = false
    var hasSpecialChar = false

    for (char in password) {
        when {
            char.isUpperCase() -> hasUppercase = true
            char.isLowerCase() -> hasLowercase = true
            char.isDigit() -> hasDigit = true
            !char.isLetterOrDigit() && !char.isWhitespace() -> hasSpecialChar = true
        }
    }*/

            if (!hasUppercase) return "Password must contain at least one uppercase letter."
            if (!hasLowercase) return "Password must contain at least one lowercase letter."
            if (!hasDigit) return "Password must contain at least one digit."
            if (!hasSpecialChar) return "Password must contain at least one special character."

            return ""
        }

        // This uses kotlinx-datetime's DateTimeFormat for robust formatting.
        fun formatDateToDDMMYYYY(date: String): String {
//            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-DD")
            val localDate = LocalDate.parse(date, formatter as DateTimeFormat<LocalDate>)
            return localDate.toString()
        }

        fun Int.formatt(): String {
            return if (this < 10 && this >= 0) "0" + this.toString() else this.toString()
        }


        fun convertTo12HourFormat(time24: String): String {
            val parts = time24.split(":")
            var hours = parts[0].toInt()
            val minutes = parts[1].toInt()

            val period: String
            if (hours >= 12) {
                period = "PM"
            } else {
                period = "AM"
            }
            hours = hours % 12
            if (hours == 0) {
                hours = 12 // Convert 0 (midnight) to 12 AM
            }
            val formattedMinutes = if (minutes < 10) "0$minutes" else minutes.formatt()
            return "${hours.formatt()}:$formattedMinutes $period"
        }


        /**
         * Checks if startTime is before endTime and calculates the total minute difference.
         *
         * @param startTime A string representing the start time in "HH:MM" 24-hour format.
         * @param endTime A string representing the end time in "HH:MM" 24-hour format.
         * @return A Pair where:
         * - first (Boolean) is true if startTime is before endTime, false otherwise.
         * - second (Int) is the total minute difference between endTime and startTime.
         * Returns -1 if there's a parsing error for either time.
         */
        fun checkAndCalculateTimeDifference(
            startTime: String,
            endTime: String
        ): Pair<Boolean, Int> {
            try {
                val startLocalTime = LocalTime.parse(startTime)
                val endLocalTime = LocalTime.parse(endTime)

                val isStartTimeBeforeEndTime = startLocalTime.isBefore(endLocalTime)

                val duration = Duration.between(startLocalTime, endLocalTime)
                val totalMinutesDifference = duration.toMinutes().toInt()

                return Pair(isStartTimeBeforeEndTime, totalMinutesDifference)

            } catch (e: DateTimeParseException) {
                // Handle cases where the time strings are not in the correct format
                println("Error parsing time: ${e.message}")
                return Pair(false, -1) // Indicate an error
            }
        }


    }

}