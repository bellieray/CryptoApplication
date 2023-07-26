package com.example.cryptoapp.domain.datasource.local

import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.utils.Result

interface CryptoCurrencyLocalDataSource {
    suspend fun getAllCryptos(): Result<List<Crypto>>
    suspend fun search(word: String): Result<List<Crypto>>
    suspend fun insertAll(infos: List<Crypto>): Result<Unit>
}