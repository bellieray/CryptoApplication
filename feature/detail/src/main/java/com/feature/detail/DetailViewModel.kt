package com.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ConsumableError
import com.example.domain.model.CryptoDetail
import com.example.domain.model.FavoriteCrypto
import com.example.domain.model.Result
import com.example.domain.usecase.AddToFavoriteUseCase
import com.example.domain.usecase.GetCryptoDetailUseCase
import com.example.domain.usecase.GetPriceUseCase
import com.example.domain.usecase.RemoveFromFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCryptoDetailUseCase: GetCryptoDetailUseCase,
    private val getPriceUseCase: GetPriceUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _detailViewState = MutableStateFlow(DetailViewState())
    val detailViewState = _detailViewState.asStateFlow()

    init {
        fetchCryptoDetail()
    }

    private fun fetchCryptoDetail() {
        _detailViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            savedStateHandle.get<String>("id")?.let { safeId ->
                when (val response = getCryptoDetailUseCase(safeId)) {
                    is Result.Success -> {
                        _detailViewState.update {
                            it.copy(
                                cryptoDetail = CryptoDetail.from(
                                    response.data,
                                    detailViewState.value.favoriteList
                                ), isLoading = false
                            )
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
    }

    fun changeRefreshTime(time: String) {
        viewModelScope.launch {
            val id = savedStateHandle.get<String>("id")
            when (val response = id?.let { getPriceUseCase(it, time) }) {
                is Result.Success -> {
                    _detailViewState.update { it.copy(currentPrice = response.data) }
                }
                is Result.Failed -> {
                    addErrorToList(response.exception)
                }
                else -> {}
            }
        }
    }

    fun setFavoriteList(favoriteList: List<FavoriteCrypto>?) {
        _detailViewState.update {
            it.copy(favoriteList = favoriteList)
        }
    }

    fun addToFavorite(cryptoDetail: CryptoDetail) {
        _detailViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = addToFavoriteUseCase(cryptoDetail)) {
                is Result.Success -> {
                    addEventToList(DetailEvent.ShowCompleteMessage("Successfully Added"))
                    fetchCryptoDetail()
                    _detailViewState.update {
                        it.copy(isAdded = true, isLoading = false)
                    }
                }
                is Result.Failed -> {
                    addErrorToList(response.exception)
                    fetchCryptoDetail()
                    _detailViewState.update { it.copy(isLoading = false) }
                }
            }
        }
    }

    fun removeFromFavorite(coinId: String) {
        _detailViewState.update { it.copy(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = removeFromFavoriteUseCase(coinId)) {
                is Result.Success -> {
                    _detailViewState.update {
                        it.copy(isLoading = false)
                    }
                    addEventToList(DetailEvent.ShowCompleteMessage("Successfully Removed"))
                    fetchCryptoDetail()
                }
                is Result.Failed -> {
                    addErrorToList(response.exception)
                    _detailViewState.update { it.copy(isLoading = false) }
                    fetchCryptoDetail()
                }
            }
        }
    }

    private fun addEventToList(viewEvent: DetailEvent) {
        val eventList = _detailViewState.value.detailEvents?.toMutableList() ?: mutableListOf()
        eventList.add(viewEvent)
        _detailViewState.value = _detailViewState.value.copy(detailEvents = eventList)
    }

    fun eventConsumed(viewEvent: DetailEvent) {
        _detailViewState.update { currentUiState ->
            val newViewEventList =
                currentUiState.detailEvents?.filterNot { it == viewEvent } ?: mutableListOf()
            currentUiState.copy(detailEvents = newViewEventList)
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
    val cryptoDetail: CryptoDetail? = null,
    val isRemoved: Boolean = false,
    val isAdded: Boolean = false,
    val favoriteList: List<FavoriteCrypto>? = null,
    val currentPrice: Double? = null,
    val detailEvents: List<DetailEvent>? = null
)

sealed class DetailEvent {
    data class ShowCompleteMessage(val completeText: String) : DetailEvent()
}