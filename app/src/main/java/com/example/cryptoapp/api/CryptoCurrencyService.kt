package com.example.cryptocurrency.api

import com.example.cryptocurrency.model.Crypto
import com.example.cryptocurrency.model.CryptoDetailResponse
import com.example.cryptocurrency.utils.Constants.COINS_BY_ID
import com.example.cryptocurrency.utils.Constants.COINS_LIST
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CryptoCurrencyService {
    @GET(COINS_LIST)
    suspend fun getCoinList(): Response<List<Crypto>>

    @GET(COINS_BY_ID)
    suspend fun getCoinById(@Path("id") coinId: String): Response<CryptoDetailResponse>
}