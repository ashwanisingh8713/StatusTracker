package com.indusjs.data.repo

import com.indusjs.domain.model.ProfileResponse
import com.indusjs.repository.IProfileRepo
import io.ktor.client.HttpClient

class ProfileRepoImpl(private val endPoint: String,
                      private val httpClient: HttpClient): IProfileRepo {
    override suspend fun getProfile(param: Any?): ProfileResponse {
        TODO("Not yet implemented")
    }

    override suspend fun editProfile(param: Any?): ProfileResponse {
        TODO("Not yet implemented")
    }
}
