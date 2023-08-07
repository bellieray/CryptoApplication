package com.feature.home

import com.example.domain.model.Crypto
import com.example.domain.model.Result
import com.example.domain.usecase.GetAllCryptosUseCase

class FakeGetAllCryptosUseCase : GetAllCryptosUseCase {
    private var testRequest = TestResponseEnum.ERROR
    fun setRequest(testRequest: TestResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun invoke(): Result<List<Crypto>> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }
}