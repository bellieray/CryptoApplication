package com.example.data.datasource

import com.example.data.api.CryptoCurrencyService
import com.example.data.cryptoCurrencies
import com.example.data.datasource.remote.CryptoCurrencyRemoteDataSourceImpl
import com.example.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.domain.repository.FirebaseRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class CoinRemoteDataSourceTest {
    @Mock
    private lateinit var cryptoApi: CryptoCurrencyService

    @Mock
    private lateinit var firebaseRepository: FirebaseRepository

    private lateinit var cryptoRemoteDataSource: CryptoCurrencyRemoteDataSource

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        cryptoRemoteDataSource = CryptoCurrencyRemoteDataSourceImpl(cryptoApi, firebaseRepository)
    }

    @Test
    fun `when get all cryptos successfully`() = runBlocking {
        Mockito.`when`(cryptoApi.getCoinList()).thenReturn(Response.success(cryptoCurrencies))
        val result = cryptoRemoteDataSource.getAllCryptos()
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Success::class.java)
    }

    @Test
    fun `when get all cryptos unsuccessfully`() = runBlocking {
        Mockito.`when`(cryptoApi.getCoinList()).thenReturn(null)
        val result = cryptoRemoteDataSource.getAllCryptos()
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Failed::class.java)
    }
}