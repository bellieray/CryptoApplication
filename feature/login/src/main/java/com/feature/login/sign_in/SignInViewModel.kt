package com.feature.login.sign_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.model.ConsumableError
import com.example.domain.model.Result
import com.example.domain.usecase.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val loginUseCase: LoginUseCase) :
    ViewModel() {
    private val _signInViewState = MutableStateFlow(SignInViewState())
    val signInViewState = _signInViewState.asStateFlow()

    fun login(email: String, password: String) {
        _signInViewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = loginUseCase(email, password)) {
                is Result.Success -> {
                    addEventToList(SignInEvent.NavigateToHome)
                    _signInViewState.update { it.copy(isLoading = false) }
                }
                is Result.Failed -> {
                    addErrorToList(response.exception)
                    _signInViewState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    private fun addEventToList(viewEvent: SignInEvent) {
        val eventList = _signInViewState.value.signInEvents?.toMutableList() ?: mutableListOf()
        eventList.add(viewEvent)
        _signInViewState.value = _signInViewState.value.copy(signInEvents = eventList)
    }

    fun eventConsumed(viewEvent: SignInEvent) {
        _signInViewState.update { currentUiState ->
            val newViewEventList =
                currentUiState.signInEvents?.filterNot { it == viewEvent } ?: mutableListOf()
            currentUiState.copy(signInEvents = newViewEventList)
        }
    }

    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                signInViewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _signInViewState.value = signInViewState.value.copy(consumableErrors = errorList)
        }
    }

    fun errorConsumed(errorId: Long) {
        _signInViewState.update { currentUiState ->
            val newConsumableError = currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError)
        }
    }
}

data class SignInViewState(
    val signInEvents: List<SignInEvent>? = null,
    val consumableErrors: List<ConsumableError>? = null,
    val isLoading: Boolean? = null
)


sealed class SignInEvent {
    object NavigateToHome : SignInEvent()
}