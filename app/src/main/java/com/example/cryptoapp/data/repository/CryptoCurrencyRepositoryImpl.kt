package com.example.cryptocurrency.data.repository

import com.example.cryptocurrency.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptocurrency.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class CryptoCurrencyRepositoryImpl @Inject constructor(
    private val cryptoCurrencyRemoteDataSource: CryptoCurrencyRemoteDataSource
) : CryptoCurrencyRepository {
    override suspend fun getAllCryptos() = cryptoCurrencyRemoteDataSource.getAllCryptos()

    override suspend fun getCryptoDetail(coinId: String) =
        cryptoCurrencyRemoteDataSource.getCryptoDetail(coinId)
}