package com.example.cryptocurrency.model

data class Prices(
    val turkishLira: Double?,
    val usd: Double?,
    val euro: Double?
) {
    companion object {
        fun from(currentPrice: CurrentPrice?) = Prices(
            turkishLira = currentPrice?.jsonMemberTry,
            usd = currentPrice?.usd,
            euro = currentPrice?.eur
        )
    }
}