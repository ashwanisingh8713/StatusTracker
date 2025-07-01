package com.indusjs.statustracker.viewmodel

import com.indusjs.data.repo.SignUpRequestBody
import com.indusjs.domain.usecase.login.SignUpUseCase
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.model.SignUpState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val signUpUseCase: SignUpUseCase, private val coroutineScope: CoroutineScope) {

    private val _signUpResponse = MutableStateFlow<SignUpState>(SignUpState(signUpResponse = ResourceUiState.Idle))
    val signUpResponse: StateFlow<SignUpState> get() = _signUpResponse

    fun signUpRequest(email: String, password: String, name: String) {
        _signUpResponse.value.copy(signUpResponse = ResourceUiState.Loading)
        val bodyParam = SignUpRequestBody(email, password, name)
        coroutineScope.launch {
            signUpUseCase(bodyParam)
                .onSuccess { _signUpResponse.value.copy(signUpResponse = ResourceUiState.Success(it)) }
                .onFailure { _signUpResponse.value.copy(signUpResponse = ResourceUiState.Error(it.message)) }
        }
    }
}