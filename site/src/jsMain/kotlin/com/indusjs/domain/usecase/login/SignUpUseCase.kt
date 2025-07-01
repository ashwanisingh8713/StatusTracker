package com.indusjs.domain.usecase.login

import com.indusjs.domain.model.SignUpResponse
import com.indusjs.domain.repo.IUserRepository
import com.indusjs.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SignUpUseCase(private val repository: IUserRepository,
                    dispatcher: CoroutineDispatcher
): BaseUseCase<Any?, SignUpResponse>(dispatcher) {

    override suspend fun block(param: Any?): SignUpResponse {
        return repository.signUp(param)
    }
}