package com.indusjs.repository

import com.indusjs.domain.model.ForgotPasswordResponse
import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.model.SignUpResponse

interface ILoginRepo {
    suspend fun signIn(params: Any?): SignInResponse
    suspend fun signUp(params: Any?): SignUpResponse
    suspend fun forgotPassword(params: Any?): ForgotPasswordResponse
}