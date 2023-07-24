package com.example.cryptocurrency.domain.repository

import com.example.cryptocurrency.model.Crypto
import com.example.cryptocurrency.model.CryptoDetailResponse
import com.example.cryptocurrency.utils.Result

interface CryptoCurrencyRepository {
    suspend fun getAllCryptos(): Result<List<Crypto>>
    suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse>
}