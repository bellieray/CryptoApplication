package com.feature.login

import com.feature.login.sign_in.SignInViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.mockito.MockitoAnnotations

// for reach runtest completely.
@ExperimentalCoroutinesApi
class SignInViewModelTest {
    private lateinit var signInViewModel: SignInViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    //    Dispatchers.setMain(Dispatchers.Unconfined)
   //     signInViewModel = SignInViewModel(FakeFirebaseRepository())
    }

    @After
    fun tearDown() {
     //   Dispatchers.resetMain()
    }
}