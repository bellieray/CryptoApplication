package com.feature.home

import com.example.domain.model.Crypto
import com.example.domain.model.Result
import com.example.domain.usecase.InsertListToDbUseCase

class FakeInsertListToDbUseCase : InsertListToDbUseCase {
    private var testRequest = TestResponseEnum.ERROR
    fun setRequest(testRequest: TestResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun invoke(infos: List<Crypto>): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }
}