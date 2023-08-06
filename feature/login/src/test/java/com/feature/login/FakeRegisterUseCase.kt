package com.feature.login

import com.example.domain.model.Result
import com.example.domain.usecase.RegisterUseCase

class FakeRegisterUseCase : RegisterUseCase {
    private var testRequest = TestResponseEnum.ERROR
    fun setRequest(testRequest: TestResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun invoke(email: String, password: String): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }
}