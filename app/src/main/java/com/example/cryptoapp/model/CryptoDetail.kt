package com.example.cryptoapp.model

data class CryptoDetail(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val price: Prices?,
    val description: String?,
    val imageUrl: String?,
    val hashingAlgorithm: String?,
    val changeLast24H: Double?
) {
    companion object {
        fun from(cryptoDetailResponse: CryptoDetailResponse?) = CryptoDetail(
            id = cryptoDetailResponse?.id,
            name = cryptoDetailResponse?.name,
            symbol = cryptoDetailResponse?.symbol,
            price = Prices.from(cryptoDetailResponse?.marketData?.currentPrice),
            description = cryptoDetailResponse?.description?.tr,
            imageUrl = cryptoDetailResponse?.image?.small,
            hashingAlgorithm = cryptoDetailResponse?.hashingAlgorithm,
            changeLast24H = cryptoDetailResponse?.marketData?.priceChangePercentage24h
        )
    }
}