package com.example.data.repository

import com.example.data.CRYPTO_ID
import com.example.data.TEST_EMAIL
import com.example.data.TEST_PASSWORD
import com.example.data.cryptoDetail
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FirebaseRepositoryTest {
    private lateinit var fakeFirebaseRepository: FakeFirebaseRepository

    @Before
    fun setup() {
        fakeFirebaseRepository = FakeFirebaseRepository()
    }

    @Test
    fun `when user login successfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeFirebaseRepository.login(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Success::class.java)
    }

    @Test
    fun `when user register unsuccessfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.ERROR)
        val result = fakeFirebaseRepository.login(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Failed::class.java)
    }


    @Test
    fun `when user register successfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeFirebaseRepository.register(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Success::class.java)
    }

    @Test
    fun `when user login unsuccessfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.ERROR)
        val result = fakeFirebaseRepository.register(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Failed::class.java)
    }


    @Test
    fun `when user add item to favorite successfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeFirebaseRepository.addToFavorites(cryptoDetail = cryptoDetail)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Success::class.java)
    }

    @Test
    fun `when user  add item to favorite unsuccessfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.ERROR)
        val result = fakeFirebaseRepository.addToFavorites(cryptoDetail)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Failed::class.java)
    }


    @Test
    fun `when user remove item from favorite successfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeFirebaseRepository.removeFromFavorites(CRYPTO_ID)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Success::class.java)
    }

    @Test
    fun `when user  remove item from favorite unsuccessfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.ERROR)
        val result = fakeFirebaseRepository.removeFromFavorites(CRYPTO_ID)
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Failed::class.java)
    }


    @Test
    fun `when user get all favorite items  successfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeFirebaseRepository.getAllFavorites()
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Success::class.java)
    }

    @Test
    fun `when user get all favorite items unsuccessfully`() = runBlocking {
        fakeFirebaseRepository.setRequest(TestResponseEnum.ERROR)
        val result = fakeFirebaseRepository.getAllFavorites()
        Truth.assertThat(result).isInstanceOf(com.example.domain.model.Result.Failed::class.java)
    }

}