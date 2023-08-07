package com.feature.detail

import com.example.domain.model.CryptoDetail
import com.google.common.annotations.VisibleForTesting


@VisibleForTesting
val cryptoDetail = CryptoDetail(
    id = "bitcoin",
    name = "Bitcoin",
    symbol = "BTC",
    price = 40000.0,
    description = "blabla",
    imageUrl = "https://example.com/images/btc-large.png",
    hashingAlgorithm = "SHA-256",
    changeLast24H = 5.3,
    isFavorite = true
)

@VisibleForTesting
enum class TestResponseEnum {
    ERROR, SUCCESS
}
