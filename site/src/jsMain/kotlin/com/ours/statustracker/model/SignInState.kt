package com.ours.statustracker.model

import com.indusjs.domain.model.SignInResponse

data class SignInState(
    val signInResponse: ResourceUiState<SignInResponse>
)