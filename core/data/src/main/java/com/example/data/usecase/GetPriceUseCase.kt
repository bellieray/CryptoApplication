package com.example.data.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.usecase.GetPriceUseCase
import javax.inject.Inject

class GetPriceUseCaseImpl @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) :
    GetPriceUseCase {
    override suspend operator fun invoke(coinId: String, refreshTime: String) =
        cryptoCurrencyRepository.getPrice(coinId, refreshTime)
}