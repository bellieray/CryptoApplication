package com.example.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.CRYPTO_ID
import com.example.data.REQUEST_DELAY_TIME
import com.example.data.api.CryptoCurrencyService
import com.example.data.cryptoCurrencies
import com.example.data.datasource.local.CryptoCurrencyLocalDataSourceImpl
import com.example.data.datasource.local.CryptoDB
import com.example.data.datasource.remote.CryptoCurrencyRemoteDataSourceImpl
import com.example.data.datasource.remote.FirebaseRemoteDataSourceImpl
import com.example.domain.datasource.local.CryptoCurrencyLocalDataSource
import com.example.domain.datasource.remote.CryptoCurrencyRemoteDataSource
import com.example.domain.datasource.remote.FirebaseRemoteDataSource
import com.example.domain.repository.FirebaseRepository
import com.google.common.truth.Truth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class CoinRepositoryTest {

    @Mock
    private lateinit var cryptoApi: CryptoCurrencyService

    lateinit var mockContext: Context

    private lateinit var fakeCoinRepository: FakeCoinRepository

    private lateinit var cryptoRemoteDataSource: CryptoCurrencyRemoteDataSource
    private lateinit var cryptoLocalDataSource: CryptoCurrencyLocalDataSource
    private lateinit var firebaseRemoteDataSource: FirebaseRemoteDataSource

    private lateinit var firebaseRepository: FirebaseRepository

    @Mock
    private lateinit var firebaseAuth: FirebaseAuth

    @Mock
    private lateinit var firebaseReference: CollectionReference

    @Before
    fun setup() {
        mockContext = ApplicationProvider.getApplicationContext()
        MockitoAnnotations.openMocks(this)
        val cryptoDatabase = Room.inMemoryDatabaseBuilder(
            mockContext,
            CryptoDB::class.java
        ).allowMainThreadQueries().build()
        val dao = cryptoDatabase.coinsDAO()
        //firebaseReference = FirebaseFirestore.getInstance().collection(FAVORITES)
        firebaseRemoteDataSource = FirebaseRemoteDataSourceImpl(firebaseAuth, firebaseReference)
        firebaseRepository = FirebaseRepositoryImpl(firebaseRemoteDataSource)
        fakeCoinRepository = FakeCoinRepository()
        cryptoRemoteDataSource = CryptoCurrencyRemoteDataSourceImpl(cryptoApi, firebaseRepository)
        cryptoLocalDataSource = CryptoCurrencyLocalDataSourceImpl(dao)

    }

    @Test
    fun whenGet_all_coins_successfully() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.getAllCryptos()
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }
    }

    @Test
    fun whenGet_all_coins_unsuccessfully() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.getAllCryptos()
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }
    }

    @Test
    fun whenGet_crypto_with_id_successfully() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.getCryptoDetail(CRYPTO_ID)
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }
    }

    @Test
    fun whenGet_crypto_with_id_unsuccessfully() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.getCryptoDetail(CRYPTO_ID)
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Failed::class.java)
        }
    }

    @Test
    fun whenGet_coinPrice_with_id_and_timer_successfully() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.getPrice(CRYPTO_ID, REQUEST_DELAY_TIME)
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }
    }

    @Test
    fun whenGet_coinPrice_with_id_and_timer_unsuccessfully() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.getPrice(CRYPTO_ID, REQUEST_DELAY_TIME)
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Failed::class.java)
        }
    }

    @Test
    fun `when get all cryptos from db successfully`() =
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.getAllCryptosFromDb()
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }

    @Test
    fun `when get all cryptos from db unsuccessfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.getAllCryptosFromDb()
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Failed::class.java)
        }
    }

    @Test
    fun `when search from db successfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.search("bit")
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }
    }

    @Test
    fun `when search from db unsuccessfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.search("bit")
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Failed::class.java)
        }
    }

    @Test
    fun `when insert all item to  db successfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.insertAll(cryptoCurrencies)
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Success::class.java)
        }
    }

    @Test
    fun `when insert all item to  db unsuccessfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.insertAll(cryptoCurrencies)
            Truth.assertThat(result)
                .isInstanceOf(com.example.domain.model.Result.Failed::class.java)
        }
    }

    @Test
    fun `when detect if price change successfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.SUCCESS)
            val result = fakeCoinRepository.detectPriceChange()
            Truth.assertThat(result?.name).isEqualTo("bittorrent")
        }
    }

    @Test
    fun `when detect if price change unsuccessfully`() {
        runBlocking {
            fakeCoinRepository.setRequest(TestResponseEnum.ERROR)
            val result = fakeCoinRepository.detectPriceChange()
            Truth.assertThat(result?.name).isEqualTo(null)
        }
    }

}


enum class TestResponseEnum {
    ERROR,
    SUCCESS
}