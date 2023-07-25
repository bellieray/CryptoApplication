package com.example.cryptoapp.data.repository

import com.example.cryptoapp.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptoapp.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoCurrencyRepositoryImpl @Inject constructor(
    private val cryptoCurrencyRemoteDataSource: CryptoCurrencyRemoteDataSource
) : CryptoCurrencyRepository {
    override suspend fun getAllCryptos() = cryptoCurrencyRemoteDataSource.getAllCryptos()

    override suspend fun getCryptoDetail(coinId: String) =
        cryptoCurrencyRemoteDataSource.getCryptoDetail(coinId)
}