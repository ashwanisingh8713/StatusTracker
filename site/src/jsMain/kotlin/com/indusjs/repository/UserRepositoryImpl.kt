package com.indusjs.repository

import com.indusjs.domain.model.ForgotPasswordResponse
import com.indusjs.domain.model.ProfileResponse
import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.model.SignUpResponse
import com.indusjs.domain.repo.IUserRepository

class UserRepositoryImpl(private val loginRepo: ILoginRepo, private val profileRepo: IProfileRepo): IUserRepository {

    override suspend fun signIn(params: Any?): SignInResponse {
        return loginRepo.signIn(params)
    }

    override suspend fun signUp(params: Any?): SignUpResponse {
        return loginRepo.signUp(params)
    }

    override suspend fun forgotPassword(params: Any?): ForgotPasswordResponse {
        return loginRepo.forgotPassword(params)
    }

    override suspend fun getProfile(params: Any?): ProfileResponse {
        return profileRepo.getProfile(params)
    }

    override suspend fun editProfile(params: Any?): ProfileResponse {
        return profileRepo.editProfile(params)
    }



}