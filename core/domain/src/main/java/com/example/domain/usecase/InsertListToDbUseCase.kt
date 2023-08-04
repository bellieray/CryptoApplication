package com.example.domain.usecase

import com.example.domain.model.Crypto
import com.example.domain.repository.CryptoCurrencyRepository
import javax.inject.Inject

interface InsertListToDbUseCase {
    suspend operator fun invoke(infos: List<Crypto>)  : com.example.domain.model.Result<Unit>
}