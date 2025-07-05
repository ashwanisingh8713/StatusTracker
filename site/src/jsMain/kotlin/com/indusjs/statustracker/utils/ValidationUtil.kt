package com.indusjs.statustracker.utils

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
    }

}