package com.example.domain.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class DetectPriceUseCase @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) {
    suspend operator fun invoke() = cryptoCurrencyRepository.detectPriceChange()
}