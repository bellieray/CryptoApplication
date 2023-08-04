package com.example.domain.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class GetAllCryptosUseCase @Inject constructor(private val currencyRepository: CryptoCurrencyRepository) {
    suspend operator fun invoke() = currencyRepository.getAllCryptos()
}