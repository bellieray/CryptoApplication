package com.example.cryptoapp.data.datasource.remote

import com.example.cryptoapp.api.CryptoCurrencyService
import com.example.cryptoapp.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetailResponse
import com.example.cryptoapp.utils.Result
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