package com.indusjs.statustracker.model

import com.indusjs.domain.model.SignInResponse

sealed interface ResourceUiState<out T> {
    data class Success<T>(val data: T) : ResourceUiState<T>
    data class Error(val message: String? = null) : ResourceUiState<Nothing>
    object Loading : ResourceUiState<Nothing>
    object Empty : ResourceUiState<Nothing>
    object Idle : ResourceUiState<Nothing>
}

