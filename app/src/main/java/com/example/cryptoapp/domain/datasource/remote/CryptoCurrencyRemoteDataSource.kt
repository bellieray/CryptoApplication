package com.example.cryptoapp.domain.datasource.remote

import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetailResponse
import com.example.cryptoapp.utils.Result

interface CryptoCurrencyRemoteDataSource {
    suspend fun getAllCryptos(): Result<List<Crypto>>
    suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse>
    suspend fun getPrice(coinId: String, refreshTime: String): Result<Double>
}