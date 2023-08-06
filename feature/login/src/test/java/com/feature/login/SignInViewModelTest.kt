package com.feature.login

import com.feature.login.sign_in.SignInEvent
import com.feature.login.sign_in.SignInViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

// for reach runtest completely.
@ExperimentalCoroutinesApi
class SignInViewModelTest {
    private lateinit var signInViewModel: SignInViewModel

    @Mock
    private lateinit var fakeLoginUseCase: FakeLoginUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        signInViewModel = SignInViewModel(fakeLoginUseCase)

    }

    @Test
    fun `login with empty fields return error`() = runTest {
        `when`(
            fakeLoginUseCase(
                "",
                ""
            )
        ).thenReturn(com.example.domain.model.Result.Failed("error"))

        // Act
        signInViewModel.login("", "")

        // Assert
        val signInViewState = signInViewModel.signInViewState.value
        Truth.assertThat(signInViewState.isLoading).isEqualTo(false)
        Truth.assertThat(signInViewState.consumableErrors?.first()?.exception).isEqualTo("error")
    }

    @Test
    fun `login with correct fields return success`() = runTest {
        val email = "test@example.com"
        val password = "password"
        `when`(
            fakeLoginUseCase(
                email,
                password
            )
        ).thenReturn(com.example.domain.model.Result.Success(Unit))

        // Act
        signInViewModel.login(email, password)

        // Assert
        val signInViewState = signInViewModel.signInViewState.value
        Truth.assertThat(signInViewState.isLoading).isEqualTo(false)
        Truth.assertThat(signInViewState.signInEvents).containsExactly(SignInEvent.NavigateToHome)
    }


    @Test
    fun `eventConsumed removes event from list`() = runTest {
        signInViewModel.addEventToList(SignInEvent.NavigateToHome)
        signInViewModel.eventConsumed(SignInEvent.NavigateToHome)

        // Assert
        val signInViewState = signInViewModel.signInViewState.value
        Truth.assertThat(signInViewState.signInEvents).isEmpty()
    }

    @Test
    fun `errorConsumed removes error from list`() = runTest {
        // Arrange
        signInViewModel.addErrorToList("errorId")
        signInViewModel.errorConsumed(
            signInViewModel.signInViewState.value.consumableErrors?.first()?.id ?: 0
        )

        // Assert
        val signInViewState = signInViewModel.signInViewState.value
        Truth.assertThat(signInViewState.consumableErrors).isEmpty()
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}