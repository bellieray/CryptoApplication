package com.example.cryptoapp.api

import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.model.CryptoDetailResponse
import com.example.cryptoapp.model.CryptoMarket
import com.example.cryptoapp.utils.Constants.COINS_BY_ID
import com.example.cryptoapp.utils.Constants.COINS_LIST
import com.example.cryptoapp.utils.Constants.COIN_MARKETS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyService {
    @GET(COINS_LIST)
    suspend fun getCoinList(): Response<List<Crypto>>

    @GET(COINS_BY_ID)
    suspend fun getCoinById(@Path("id") coinId: String): Response<CryptoDetailResponse>

    @GET(COIN_MARKETS)
    suspend fun getCoinMarkets(): Response<List<CryptoMarket>>
}