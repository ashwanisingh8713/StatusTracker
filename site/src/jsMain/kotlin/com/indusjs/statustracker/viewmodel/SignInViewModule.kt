package com.indusjs.statustracker.viewmodel

import com.indusjs.data.repo.SignInRequestBody
import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.usecase.login.SignInUseCase
import com.indusjs.statustracker.model.ResourceUiState
import com.indusjs.statustracker.model.SignInState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModule(private val signInUseCase: SignInUseCase, private val coroutineScope: CoroutineScope) {

    private val _loginState = MutableStateFlow<SignInState>(
        value = SignInState(signInResponse = ResourceUiState.Idle)
    )
    val loginState: StateFlow<SignInState> get() = _loginState

    fun signInRequest(email: String, password: String) {
        // Loading state is initiated
        _loginState.value.copy(signInResponse = ResourceUiState.Loading)
        val bodyParam = SignInRequestBody(email, password)
        coroutineScope.launch {
            signInUseCase(bodyParam)
                .onSuccess<SignInResponse> {
                    _loginState.value.copy(signInResponse = ResourceUiState.Success(it))
                }
                .onFailure{
                    _loginState.value.copy(signInResponse = ResourceUiState.Error(it.message))
                }
        }
    }

}