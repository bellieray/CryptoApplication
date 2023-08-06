package com.core.common.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.ConsumableError
import com.example.domain.model.FavoriteCrypto
import com.example.domain.model.Result
import com.example.domain.usecase.GetAllFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val getAllFavoritesUseCase: GetAllFavoritesUseCase) :
    ViewModel() {
    private val _favoriteViewState = MutableStateFlow(FavoriteViewState())
    var favoriteViewState = _favoriteViewState.asStateFlow()

    fun fetchFavorites() {
        _favoriteViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = getAllFavoritesUseCase.invoke()) {
                is Result.Success -> {
                    _favoriteViewState.emit(
                        FavoriteViewState(
                            favoriteList = response.data,
                            isLoading = false
                        )
                    )
                }
                is Result.Failed -> {
                    _favoriteViewState.update { it.copy(isLoading = false) }
                    addErrorToList(response.exception.toString())
                }
            }
        }
    }

    private fun addErrorToList(exception: String?) {
        exception?.let {
            val errorList =
                _favoriteViewState.value.consumableErrors?.toMutableList() ?: mutableListOf()
            errorList.add(ConsumableError(exception = it))
            _favoriteViewState.value = _favoriteViewState.value.copy(consumableErrors = errorList)
        }
    }

    fun errorConsumed(errorId: Long) {
        _favoriteViewState.update { currentUiState ->
            val newConsumableError = currentUiState.consumableErrors?.filterNot { it.id == errorId }
            currentUiState.copy(consumableErrors = newConsumableError)
        }
    }
}

data class FavoriteViewState(
    val favoriteList: List<FavoriteCrypto>? = null,
    val consumableErrors: List<ConsumableError>? = null,
    val isLoading: Boolean? = null,
)
