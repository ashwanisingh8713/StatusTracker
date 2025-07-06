package com.indusjs.data.storage

import com.indusjs.domain.model.SignInResponse
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

object UserStorage {
    // Constants for localStorage keys
    private val LOCAL_STORAGE_USER_KEY = "userInfo"


    fun saveUserInfoToLocalStorage(userInfo: SignInResponse) {
        try {
            // Convert the UserInfo object to a JSON string
            val jsonString = Json.encodeToString(userInfo)
            window.localStorage.setItem(LOCAL_STORAGE_USER_KEY, jsonString)
            println("User info saved to localStorage: $jsonString")
        } catch (e: Exception) {
            println("Error saving user info to localStorage: ${e.message}")
        }
    }

    fun loadUserInfoFromLocalStorage(): SignInResponse? {
        try {
            // Retrieve the JSON string from localStorage
            val jsonString = window.localStorage.getItem(LOCAL_STORAGE_USER_KEY)
            if (jsonString != null) {
                // Convert the JSON string back to a UserInfo object
                return Json.decodeFromString<SignInResponse>(jsonString)
            }
        } catch (e: Exception) {
            println("Error loading user info from localStorage: ${e.message}")
            // Clear invalid data if parsing fails to prevent future errors
            //clearUserInfoFromLocalStorage()
        }
        return null
    }

    fun clearUserInfoFromLocalStorage() {
        window.localStorage.removeItem(LOCAL_STORAGE_USER_KEY)
        println("User info cleared from localStorage.")
    }

}