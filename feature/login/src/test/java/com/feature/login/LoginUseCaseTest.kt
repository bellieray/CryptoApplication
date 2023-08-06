package com.feature.login

import com.example.domain.model.Result
import com.feature.TEST_EMAIL
import com.feature.TEST_PASSWORD
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private lateinit var fakeLoginUseCase: FakeLoginUseCase

    @Before
    fun setup() {
        fakeLoginUseCase = FakeLoginUseCase()
    }


    @Test
    fun `when user login successfully`() = runBlocking {
        fakeLoginUseCase.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeLoginUseCase.invoke(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `when user login unsuccessfully`() = runBlocking {
        fakeLoginUseCase.setRequest(TestResponseEnum.ERROR)
        val result = fakeLoginUseCase.invoke(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(Result.Failed::class.java)
    }
}