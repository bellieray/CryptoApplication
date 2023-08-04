package com.example.data.usecase

import com.example.domain.model.Crypto
import com.example.domain.repository.CryptoCurrencyRepository
import com.example.domain.usecase.GetAllCryptosUseCase
import javax.inject.Inject

class GetAllCryptosUseCaseImpl @Inject constructor(private val currencyRepository: CryptoCurrencyRepository) :
    GetAllCryptosUseCase {
    override suspend operator fun invoke(): com.example.domain.model.Result<List<Crypto>> =
        currencyRepository.getAllCryptos()
}