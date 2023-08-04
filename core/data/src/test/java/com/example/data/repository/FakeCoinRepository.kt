package com.example.data.repository

import com.example.data.cryptoCurrencies
import com.example.data.cryptoDetailResponse
import com.example.domain.model.Crypto
import com.example.domain.model.CryptoDetailResponse
import com.example.domain.model.Result
import com.example.domain.repository.CryptoCurrencyRepository

class FakeCoinRepository() : CryptoCurrencyRepository {
    private var testRequest = TestResponseEnum.ERROR

    fun setRequest(testRequest: TestResponseEnum) {
        this.testRequest = testRequest
    }

    override suspend fun getAllCryptos(): Result<List<Crypto>> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success(cryptoCurrencies)
        }
    }

    override suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success(cryptoDetailResponse)
        }
    }

    override suspend fun getPrice(coinId: String, refreshTime: String): Result<Double> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }

    override suspend fun getAllCryptosFromDb(): Result<List<Crypto>> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success(cryptoCurrencies)
        }
    }

    override suspend fun search(word: String): Result<List<Crypto>> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success(cryptoCurrencies)
        }
    }

    override suspend fun insertAll(infos: List<Crypto>): Result<Unit> {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Result.Failed("error")
            TestResponseEnum.SUCCESS -> Result.Success()
        }
    }

    override suspend fun detectPriceChange(): Crypto? {
        return when (testRequest) {
            TestResponseEnum.ERROR -> Crypto("0",null,null)
            TestResponseEnum.SUCCESS -> Crypto("1","bit","bittorrent")
        }
    }

}