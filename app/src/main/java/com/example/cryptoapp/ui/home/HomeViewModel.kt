package com.example.cryptoapp.ui.home

import androidx.lifecycle.SavedStateHandle
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

    private fun fetchCryptoList() {
        _homeViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = cryptoCurrencyRepository.getAllCryptos()) {
                is Result.Success -> {
                    _homeViewState.update {
                        it.copy(cryptoList = response.data, isLoading = false)
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
}

data class HomeViewState(
    val cryptoList: List<Crypto>? = null,
    val isLoading: Boolean? = null
)