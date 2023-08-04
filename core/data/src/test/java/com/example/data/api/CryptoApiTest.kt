package com.example.data.api

import com.example.data.COIN_MARKETS_RESPONSE_FILE_NAME
import com.example.data.CRYPTO_ID
import com.example.data.CRYPTO_RESPONSE_FILE_NAME
import com.example.data.SINGLE_CRYPTO_RESPONSE_FILE_NAME
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class AnimeApiTest {
    private val mockWebServer: MockWebServer = MockWebServer()

    private lateinit var cryptoApi: CryptoCurrencyService

    @Before
    fun setup() {
        mockWebServer.start(port = 8000)
        cryptoApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CryptoCurrencyService::class.java)
    }

    @Test
    fun response_whenAllCryptos_isNotNull() {
        runBlocking {
            enqueueMockResponse(CRYPTO_RESPONSE_FILE_NAME)
            val response = cryptoApi.getCoinList()
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenAllCryptosRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(CRYPTO_RESPONSE_FILE_NAME)
            cryptoApi.getCoinList()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/coins/list")
        }
    }

    @Test
    fun firstElement_whenAllCryptosRequested_hasSameName() {
        runBlocking {
            enqueueMockResponse(CRYPTO_RESPONSE_FILE_NAME)
            val response = cryptoApi.getCoinList()
            val firstItem = response.body()?.first()
            assertThat(firstItem?.name).isEqualTo("01coin")
        }
    }

    @Test
    fun response_whenCryptoRequestedWithID_isNotNull() {
        runBlocking {
            enqueueMockResponse(SINGLE_CRYPTO_RESPONSE_FILE_NAME)
            val response = cryptoApi.getCoinById(CRYPTO_ID)
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenCryptoRequestedWithID_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(SINGLE_CRYPTO_RESPONSE_FILE_NAME)
            cryptoApi.getCoinById(CRYPTO_ID)
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/coins/$CRYPTO_ID?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false")
        }
    }

    @Test
    fun firstElement_whenCryptoRequestedWithID_isSame() {
        runBlocking {
            enqueueMockResponse(SINGLE_CRYPTO_RESPONSE_FILE_NAME)
            val response = cryptoApi.getCoinById(CRYPTO_ID)
            val firstItem = response.body()
            assertThat(firstItem?.name).isEqualTo("0xCoco")
        }
    }

    @Test
    fun response_whenCoinMarketsRequested_isNotNull() {
        runBlocking {
            enqueueMockResponse(COIN_MARKETS_RESPONSE_FILE_NAME)
            val response = cryptoApi.getCoinMarkets()
            mockWebServer.takeRequest()
            assertThat(response).isNotNull()
        }
    }

    @Test
    fun requestPath_whenCoinMarketsRequested_isSameWithRequest() {
        runBlocking {
            enqueueMockResponse(COIN_MARKETS_RESPONSE_FILE_NAME)
            cryptoApi.getCoinMarkets()
            val request = mockWebServer.takeRequest()
            assertThat(request.path).isEqualTo("/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false")
        }
    }

    @Test
    fun firstElement_whenCoinMarketsRequested_isSame() {
        runBlocking {
            enqueueMockResponse(COIN_MARKETS_RESPONSE_FILE_NAME)
            val response = cryptoApi.getCoinMarkets()
            val firstItem = response.body()?.first()
            assertThat(firstItem?.name).isEqualTo("Bitcoin")
        }
    }

    private fun enqueueMockResponse(serverResponseFileName: String) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(serverResponseFileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            mockWebServer.enqueue(mockResponse)
        }
    }

}