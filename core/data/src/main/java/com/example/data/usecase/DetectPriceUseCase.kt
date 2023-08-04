package com.example.data.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.usecase.DetectPriceUseCase
import javax.inject.Inject

class DetectPriceUseCaseImpl @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) :
    DetectPriceUseCase {
    override suspend operator fun invoke() = cryptoCurrencyRepository.detectPriceChange()
}