package com.example.cryptoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.repository.CryptoCurrencyRepository
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

    init {
        fetchCryptoList()
    }

     fun fetchCryptoList() {
        viewModelScope.launch {
            when (val response = cryptoCurrencyRepository.getAllCryptos()) {
                is Result.Success -> {
                    _homeViewState.update {
                        response.data?.let { data -> insertItemToDb(data) }
                        it.copy(isLoading = false)
                    }
                }
                else -> {
                }
            }
        }
    }

    private fun insertItemToDb(list: List<Crypto>) {
        viewModelScope.launch {
            when (cryptoCurrencyRepository.insertAll(list)) {
                is Result.Success -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                    fetchCryptoFromDb()
                }
                else -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
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
                            cryptoList = response.data, isLoading = false
                        )
                    }
                }
                is Result.Failed -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    private fun fetchCryptoFromDb() {
        _homeViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = cryptoCurrencyRepository.getAllCryptosFromDb()) {
                is Result.Success -> {
                    _homeViewState.update {
                        it.copy(isLoading = false, cryptoList = response.data)
                    }
                }
                else -> {
                    _homeViewState.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }

    fun clearSuggestionHistory() {
        viewModelScope.launch {
            _homeViewState.update {
                it.copy(cryptoList = null)
            }
        }
    }
}

data class HomeViewState(
    val cryptoList: List<Crypto>? = null,
    val isLoading: Boolean? = null,
    val infoMessage: String? = null
)