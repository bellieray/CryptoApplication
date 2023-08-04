package com.example.data.usecase

import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.usecase.GetCryptoDetailUseCase
import javax.inject.Inject

class GetCryptoDetailUseCaseImpl @Inject constructor(private val cryptoCurrencyRepository: CryptoCurrencyRepository) :
    GetCryptoDetailUseCase {
    override suspend operator fun invoke(coinId: String) =
        cryptoCurrencyRepository.getCryptoDetail(coinId)
}