package com.feature.detail

import com.example.domain.model.Result
import com.example.domain.usecase.GetPriceUseCase

class FakeGetPriceUseCase : GetPriceUseCase {
    private var testRequest = TestResponseEnum.ERROR
    fun setRequest(testRequest: TestResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun invoke(coinId: String, refreshTime: String): Result<Double> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }
}