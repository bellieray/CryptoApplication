package com.feature.login

import com.example.domain.model.Result
import com.feature.TEST_EMAIL
import com.feature.TEST_PASSWORD
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FakeRegisterUseCaseTest {
    private lateinit var fakeRegisterUseCase: FakeRegisterUseCase

    @Before
    fun setup() {
        fakeRegisterUseCase = FakeRegisterUseCase()
    }

    @Test
    fun `when user register successfully`() = runBlocking {
        fakeRegisterUseCase.setRequest(TestResponseEnum.SUCCESS)
        val result = fakeRegisterUseCase(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(Result.Success::class.java)
    }

    @Test
    fun `when user register unsuccessfully`() = runBlocking {
        fakeRegisterUseCase.setRequest(TestResponseEnum.ERROR)
        val result = fakeRegisterUseCase.invoke(TEST_EMAIL, TEST_PASSWORD)
        Truth.assertThat(result).isInstanceOf(Result.Failed::class.java)
    }
}