package com.example.domain.usecase

import com.example.domain.model.Crypto
import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class InsertListToDbUseCase @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) {
    suspend operator fun invoke(infos: List<Crypto>) = cryptoCurrencyRepository.insertAll(infos)
}