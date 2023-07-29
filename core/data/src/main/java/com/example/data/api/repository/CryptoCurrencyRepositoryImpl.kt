package com.example.data.api.repository

import  com.example.domain.model.Result
import com.example.domain.datasource.local.CryptoCurrencyLocalDataSource
import com.example.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.domain.model.Crypto
import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CryptoCurrencyRepositoryImpl @Inject constructor(
    private val cryptoCurrencyRemoteDataSource: CryptoCurrencyRemoteDataSource,
    private val cryptoCurrencyLocalDataSource: CryptoCurrencyLocalDataSource
) : CryptoCurrencyRepository {
    override suspend fun getAllCryptos() = cryptoCurrencyRemoteDataSource.getAllCryptos()

    override suspend fun getCryptoDetail(coinId: String) =
        cryptoCurrencyRemoteDataSource.getCryptoDetail(coinId)

    override suspend fun getPrice(coinId: String, refreshTime: String): Result<Double> =
        cryptoCurrencyRemoteDataSource.getPrice(coinId, refreshTime)

    override suspend fun getAllCryptosFromDb(): Result<List<Crypto>> =
        cryptoCurrencyLocalDataSource.getAllCryptos()

    override suspend fun search(word: String): Result<List<Crypto>> =
        cryptoCurrencyLocalDataSource.search(word)

    override suspend fun insertAll(infos: List<Crypto>): Result<Unit> =
        cryptoCurrencyLocalDataSource.insertAll(infos)

    override suspend fun detectPriceChange(): Crypto? =
        cryptoCurrencyRemoteDataSource.detectPriceChange()

}