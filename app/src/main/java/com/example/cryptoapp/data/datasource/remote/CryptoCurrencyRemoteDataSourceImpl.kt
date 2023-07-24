package com.example.cryptocurrency.data.datasource.remote

import com.example.cryptocurrency.api.CryptoCurrencyService
import com.example.cryptocurrency.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptocurrency.model.Crypto
import com.example.cryptocurrency.model.CryptoDetailResponse
import com.example.cryptocurrency.utils.Result
import retrofit2.HttpException
import javax.inject.Inject

class CryptoCurrencyRemoteDataSourceImpl @Inject constructor(private val cryptoCurrencyService: CryptoCurrencyService) :
    CryptoCurrencyRemoteDataSource {
    override suspend fun getAllCryptos(): Result<List<Crypto>> {
        return try {
            val response = cryptoCurrencyService.getCoinList()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Failed(response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            Result.Failed(e.message().toString())
        } catch (e: Throwable) {
            Result.Failed(e.localizedMessage.toString())
        }
    }

    override suspend fun getCryptoDetail(coinId: String): Result<CryptoDetailResponse> {
        return try {
            val response = cryptoCurrencyService.getCoinById(coinId)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body)
            } else {
                Result.Failed(response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            Result.Failed(e.message().toString())
        } catch (e: Throwable) {
            Result.Failed(e.localizedMessage.toString())
        }
    }
}