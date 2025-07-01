package com.indusjs.data.repo

import com.indusjs.domain.model.ForgotPasswordResponse
import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.model.SignUpResponse
import com.indusjs.repository.ILoginRepo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable

class LoginRepoImpl(private val endPoint: String,
                    private val httpClient: HttpClient): ILoginRepo {

    override suspend fun signIn(param: Any?): SignInResponse {
        val bodyParam = param as? SignInRequestBody
        val response: HttpResponse = httpClient.post() {
            url("$endPoint/auth/login")
            setBody(bodyParam)
            contentType(ContentType.Application.Json)
        }
        return if(response.status.isSuccess()) {
            response.body<SignInResponse>()
        } else {
            throw IllegalArgumentException(response.status.toString())
            /*SignInResponse(
                success = false,
                message = response.status.toString(),
            )*/
        }
    }

    override suspend fun signUp(param: Any?): SignUpResponse {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword(param: Any?): ForgotPasswordResponse {
        TODO("Not yet implemented")
    }


}

@Serializable
data class SignInRequestBody(
    val email: String,
    val password: String,
)

@Serializable
data class SignUpRequestBody(
    val email: String,
    val password: String,
    val name:String
)

@Serializable
sealed class  UserType {
    data class Security(val type:String = "security") : UserType()
    data class Residential(val type:String = "residential") : UserType()
    data class None(val type:String = "None") : UserType()
}