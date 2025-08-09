package com.indusjs.domain.repo

import com.indusjs.domain.model.ForgotPasswordResponse
import com.indusjs.domain.model.ProfileResponse
import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.model.SignUpResponse

interface IUserRepository {
    suspend fun signIn(params: Any?): SignInResponse
    suspend fun signUp(params: Any?): SignUpResponse
    suspend fun forgotPassword(params: Any?): ForgotPasswordResponse
    suspend fun getProfile(params: Any?): ProfileResponse
    suspend fun editProfile(params: Any?): ProfileResponse
}