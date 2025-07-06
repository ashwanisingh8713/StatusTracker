package com.indusjs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(val email: String = "",
                          val id: Int = -1,
                          val name: String = "",
                          val token: String = "")


