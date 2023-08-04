package com.example.domain.usecase

import com.example.domain.model.Crypto

interface SearchUseCase {
    suspend operator fun invoke(word: String): com.example.domain.model.Result<List<Crypto>>

}