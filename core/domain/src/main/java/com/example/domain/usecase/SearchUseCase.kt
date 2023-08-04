package com.example.domain.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(val currencyRepository: CryptoCurrencyRepository) {
    suspend operator fun invoke(word: String) = currencyRepository.search(word)
}