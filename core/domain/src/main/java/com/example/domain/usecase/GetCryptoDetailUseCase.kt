package com.example.domain.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

class GetCryptoDetailUseCase @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) {
    suspend operator fun invoke(coinId: String) = cryptoCurrencyRepository.getCryptoDetail(coinId)
}