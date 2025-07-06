package com.indusjs.data.auth

import com.indusjs.data.storage.UserStorage
import com.indusjs.domain.model.SignInResponse

object AuthManager {
    // This is the correct way to define the property
    private var signInResponse: SignInResponse? = null

    // Example function to simulate a successful sign-in
    fun onSignInSuccess(response: SignInResponse) {
        UserStorage.saveUserInfoToLocalStorage(response)
        signInResponse = response
        println("Sign-in response set: ${signInResponse?.id}")
    }

    fun getCurrentUserToken(): String? {
        if(signInResponse == null) {
            signInResponse = UserStorage.loadUserInfoFromLocalStorage()
        }
        return signInResponse?.token
    }

    fun getCurrentUserEmail():String? {
        if(signInResponse == null) {
            signInResponse = UserStorage.loadUserInfoFromLocalStorage()
        }
        return signInResponse?.email
    }

    // Example function to retrieve user ID
    fun getCurrentUserId(): String? {
        if(signInResponse == null) {
            signInResponse = UserStorage.loadUserInfoFromLocalStorage()
        }
        return signInResponse?.id.toString() // Accessing the getter to retrieve the value
    }

    // Example function to check if a user is signed in
    fun isSignedIn(): Boolean {
        if(signInResponse == null) {
            signInResponse = UserStorage.loadUserInfoFromLocalStorage()
        }
        return signInResponse != null // Accessing the getter
    }

    // Example function to clear the sign-in data
    fun signOut() {
        UserStorage.clearUserInfoFromLocalStorage()
        signInResponse = null // Accessing the setter to clear the value
        println("User signed out.")
    }

}