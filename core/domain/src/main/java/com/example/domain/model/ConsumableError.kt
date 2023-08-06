package com.example.domain.model

import java.util.*

data class ConsumableError(
    val id: Long = UUID.randomUUID().mostSignificantBits,
    val exception: String
)