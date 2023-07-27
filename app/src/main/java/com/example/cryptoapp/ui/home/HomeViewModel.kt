package com.example.cryptoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.repository.CryptoCurrencyRepository
import com.example.cryptoapp.model.ConsumableError
import com.example.cryptoapp.model.Crypto
import com.example.cryptoapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cryptoCurrencyRepository: CryptoCurrencyRepository
) :
    ViewModel() {
    private val _homeViewState = MutableStateFlow(HomeViewState())
    val homeViewState = _homeViewState.asStateFlow()

    fun fetchCryptoList() {
        if (_homeViewState.value.cryptoList != null) return
        _homeViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = cryptoCurrencyRepository.getAllCryptos()) {
                is Result.Success -> {
                    _homeViewState.update {
                        response.data?.let { data -> insertItemToDb(data) }
                        it.copy(isLoading = false, cryptoList = response.data)
                    }
                }
                is Result.Failed -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                    addErrorToList(response.exception)
                }
            }
        }
    }

    private fun insertItemToDb(list: List<Crypto>) {
        viewModelScope.launch {
            when (val response = cryptoCurrencyRepository.insertAll(list)) {
                is Result.Success -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                }
                is Result.Failed -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                    addErrorToList(response.exception)
                }
            }
        }
    }

    fun search(text: String) {
        _homeViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = cryptoCurrencyRepository.search(text)) {
                is Result.Success -> {
                    _homeViewState.update {
                        it.copy(
                            searchResultList = response.data, isLoading = false
                        )
                    }
                }
                is Result.Failed -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                    addErrorToList(response.exception)
                }
            }
        }
    }

    fun clearSuggestionHistory() {
        viewModelScope.launch {
            _homeViewState.update {
                it.copy(searchResultList = null)
            }
        }
    }


    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                _homeViewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _homeViewState.value = _homeViewState.value.copy(consumableErrors = errorList)
        }
    }

    fun errorConsumed(errorId: Long) {
        _homeViewState.update { currentUiState ->
            val newConsumableError = currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError)
        }
    }
}

data class HomeViewState(
    val cryptoList: List<Crypto>? = null,
    val isLoading: Boolean = false,
    val infoMessage: String? = null,
    val searchResultList: List<Crypto>? = null,
    val consumableErrors: List<ConsumableError>? = null,
)