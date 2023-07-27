package com.example.cryptoapp.data.datasource.remote

import com.example.cryptoapp.api.CryptoCurrencyService
import com.example.cryptoapp.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.cryptoapp.domain.repository.FirebaseRepository
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetailResponse
import com.example.cryptoapp.utils.Result
import kotlinx.coroutines.delay
import retrofit2.HttpException
import javax.inject.Inject

class CryptoCurrencyRemoteDataSourceImpl @Inject constructor(
    private val cryptoCurrencyService: CryptoCurrencyService,
    private val firebaseRepository: FirebaseRepository
) :
    CryptoCurrencyRemoteDataSource {
    override suspend fun getAllCryptos(): Result<List<Crypto>> {
        return try {
            val response = cryptoCurrencyService.getCoinList()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Result.Success(body.take(25))
            } else {
                Result.Failed(response.errorBody()?.string())
            }
        } catch (e: HttpException) {
            Result.Failed(e.message().toString())
        } catch (e: Throwable) {
            Result.Failed(e.localizedMessage)
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
            Result.Failed(e.localizedMessage)
        }
    }

    override suspend fun getPrice(coinId: String, refreshTime: String): Result<Double> {
        return try {
            val data =
                cryptoCurrencyService.getCoinById(coinId).body()?.marketData?.currentPrice?.usd
            delay(refreshTime.toInt() * 1000L)
            Result.Success(data)
        } catch (e: Exception) {
            Result.Failed(e.localizedMessage)
        }
    }

    override suspend fun detectPriceChange(): Crypto? {
        val favoriteCoins = firebaseRepository.getAllFavorites()
        val responseCoins = cryptoCurrencyService.getCoinList().body()
        val marketCoins = cryptoCurrencyService.getCoinMarkets().body()
        var result: Crypto? = null
        when (favoriteCoins) {
            is Result.Success -> {
                favoriteCoins.data?.forEach { favorite ->
                    responseCoins?.forEach { responseCoins ->
                        if (favorite.id == responseCoins.id) {
                            if (favorite.price != marketCoins?.find { it.id == responseCoins.id }?.currentPrice) {
                                result = responseCoins
                            }
                        }
                    }
                }
            }
            else -> {}
        }
        return result
    }
}