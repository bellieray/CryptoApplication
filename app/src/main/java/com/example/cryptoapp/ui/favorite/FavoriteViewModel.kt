package com.example.cryptoapp.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.repository.FirebaseRepository
import com.example.cryptoapp.model.ConsumableError
import com.example.cryptoapp.model.FavoriteCrypto
import com.example.cryptoapp.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(val firebaseRepository: FirebaseRepository) :
    ViewModel() {
    private val _favoriteViewState = MutableStateFlow(FavoriteViewState())
    var favoriteViewState = _favoriteViewState.asStateFlow()

    init {
        fetchFavorites()
    }

    fun fetchFavorites() {
        _favoriteViewState.update {
            it.copy(isLoading = true)
        }
        viewModelScope.launch {
            when (val response = firebaseRepository.getAllFavorites()) {
                is Result.Success -> {
                    _favoriteViewState.emit(
                        FavoriteViewState(
                            favoriteList = response.data,
                            isLoading = false
                        )
                    )
                }
                is Result.Failed -> {
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
