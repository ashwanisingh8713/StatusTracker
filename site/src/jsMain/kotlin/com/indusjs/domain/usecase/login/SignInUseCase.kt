package com.indusjs.domain.usecase.login

import com.indusjs.domain.model.SignInResponse
import com.indusjs.domain.repo.IUserRepository
import com.indusjs.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SignInUseCase(
    private val repository: IUserRepository,
    dispatcher: CoroutineDispatcher
): BaseUseCase<Any?, SignInResponse>(dispatcher) {

    override suspend fun block(param: Any?): SignInResponse {
        return repository.signIn(param)
    }
}