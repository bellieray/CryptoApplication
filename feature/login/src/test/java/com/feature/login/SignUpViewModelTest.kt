package com.feature.login

import com.feature.login.sign_up.SignUpEvent
import com.feature.login.sign_up.SignUpViewModel
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SignUpViewModelTest {
    private lateinit var signUpViewModel: SignUpViewModel

    @Mock
    private lateinit var fakeRegisterUseCase: FakeRegisterUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        signUpViewModel = SignUpViewModel(fakeRegisterUseCase)
    }

    @Test
    fun `register with empty fields return error`() = runTest {
        Mockito.`when`(
            fakeRegisterUseCase(
                "",
                ""
            )
        ).thenReturn(com.example.domain.model.Result.Failed("error"))

        // Act
        signUpViewModel.register("", "")

        // Assert
        val signUpViewState = signUpViewModel.signUpViewState.value
        Truth.assertThat(signUpViewState.isLoading).isEqualTo(false)
        Truth.assertThat(signUpViewState.consumableErrors?.first()?.exception).isEqualTo("error")
    }

    @Test
    fun `register with correct fields return success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        Mockito.`when`(
            fakeRegisterUseCase(
                email,
                password
            )
        ).thenReturn(com.example.domain.model.Result.Success(Unit))

        // Act
        signUpViewModel.register(email, password)

        // Assert
        val signInViewState = signUpViewModel.signUpViewState.value
        Truth.assertThat(signInViewState.isLoading).isEqualTo(false)
        Truth.assertThat(signInViewState.signUpEvents).containsExactly(SignUpEvent.NavigateToSignIn)
    }

    @Test
    fun `eventConsumed removes event from list`() = runTest {
        signUpViewModel.addEventToList(SignUpEvent.NavigateToSignIn)
        signUpViewModel.eventConsumed(SignUpEvent.NavigateToSignIn)

        // Assert
        val signInViewState = signUpViewModel.signUpViewState.value
        Truth.assertThat(signInViewState.signUpEvents).isEmpty()
    }

    @Test
    fun `errorConsumed removes error from list`() = runTest {
        // Arrange
        signUpViewModel.addErrorToList("errorId")
        signUpViewModel.errorConsumed(
            signUpViewModel.signUpViewState.value.consumableErrors?.first()?.id ?: 0
        )

        // Assert
        val signInViewState = signUpViewModel.signUpViewState.value
        Truth.assertThat(signInViewState.consumableErrors).isEmpty()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}