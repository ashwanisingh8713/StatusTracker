package com.indusjs.repository

import com.indusjs.domain.model.ProfileResponse

interface IProfileRepo {
    suspend fun getProfile(params: Any?): ProfileResponse

    suspend fun editProfile(params: Any?): ProfileResponse
}