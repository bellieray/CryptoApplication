package com.example.cryptoapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.repository.CryptoCurrencyRepository
import com.example.cryptoapp.model.ConsumableError
import com.example.cryptoapp.model.CryptoDetail
import com.example.cryptoapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(val repository: CryptoCurrencyRepository) : ViewModel() {
    private val _detailViewState = MutableStateFlow(DetailViewState())
    val detailViewState = _detailViewState.asStateFlow()

    fun fetchCryptoDetail(coinId: String) {
        _detailViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = repository.getCryptoDetail(coinId)) {
                is Result.Success -> {
                    _detailViewState.update {
                        it.copy(cryptoDetail = CryptoDetail.from(response.data), isLoading = false)
                    }
                }
                is Result.Failed -> {
                    _detailViewState.update {
                        it.copy(isLoading = false)
                    }
                    addErrorToList(response.exception)
                }
            }
        }
    }

    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                _detailViewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _detailViewState.value = _detailViewState.value.copy(consumableErrors = errorList)
        }
    }

    fun errorConsumed(errorId: Long) {
        _detailViewState.update { currentUiState ->
            val newConsumableError = currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError)
        }
    }
}

data class DetailViewState(
    val consumableErrors: List<ConsumableError>? = null,
    val isLoading: Boolean? = null,
    val cryptoDetail: CryptoDetail? = null
)