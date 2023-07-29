package com.example.cryptoapp.model

import java.util.*

data class ConsumableError(val id: Long = UUID.randomUUID().mostSignificantBits, val exception: String)