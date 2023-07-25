package com.example.cryptoapp.ui.login.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.repository.FirebaseRepository
import com.example.cryptoapp.model.ConsumableError
import com.example.cryptoapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {
    private val _signUpViewState = MutableStateFlow(SignUpViewState())
    val signUpViewState = _signUpViewState.asStateFlow()

    fun register(email: String, password: String) {
        _signUpViewState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            when (val response = firebaseRepository.register(email, password)) {
                is Result.Success -> {
                    addEventToList(SignUpEvent.NavigateToSignIn)
                    _signUpViewState.update { it.copy(isLoading = false) }
                }
                is Result.Failed -> {
                    addErrorToList(response.exception)
                    _signUpViewState.update { it.copy(isLoading = true) }
                }
            }
        }
    }

    private fun addEventToList(viewEvent: SignUpEvent) {
        val eventList = signUpViewState.value.signUpEvents?.toMutableList() ?: mutableListOf()
        eventList.add(viewEvent)
        _signUpViewState.value = signUpViewState.value.copy(signUpEvents = eventList)
    }

    fun eventConsumed(viewEvent: SignUpEvent) {
        _signUpViewState.update { currentUiState ->
            val newViewEventList =
                currentUiState.signUpEvents?.filterNot { it == viewEvent } ?: mutableListOf()
            currentUiState.copy(signUpEvents = newViewEventList)
        }
    }

    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                _signUpViewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _signUpViewState.value = _signUpViewState.value.copy(consumableErrors = errorList)
        }
    }

    fun errorConsumed(errorId: Long) {
        _signUpViewState.update { currentUiState ->
            val newConsumableError = currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError)
        }
    }
}

data class SignUpViewState(
    val signUpEvents: List<SignUpEvent>? = null,
    val consumableErrors: List<ConsumableError>? = null,
    val isLoading: Boolean? = null
)

sealed class SignUpEvent {
    object NavigateToSignIn : SignUpEvent()
}