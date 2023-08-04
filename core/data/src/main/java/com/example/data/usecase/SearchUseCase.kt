package com.example.data.usecase

import com.example.domain.model.Crypto
import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.usecase.SearchUseCase
import javax.inject.Inject

class SearchUseCaseImpl @Inject constructor(private val currencyRepository: CryptoCurrencyRepository) :
    SearchUseCase {
    override suspend fun invoke(word: String): com.example.domain.model.Result<List<Crypto>> =
        currencyRepository.search(word)
}