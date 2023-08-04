package com.example.domain.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class GetPriceUseCase @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) {
    suspend operator fun invoke(coinId: String, refreshTime: String) =
        cryptoCurrencyRepository.getPrice(coinId, refreshTime)
}