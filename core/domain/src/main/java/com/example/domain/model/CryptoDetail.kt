package com.example.domain.model

data class CryptoDetail(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val price: Double?,
    val description: String?,
    val imageUrl: String?,
    val hashingAlgorithm: String?,
    val changeLast24H: Double?,
    val isFavorite: Boolean?,
) {
    companion object {
        fun from(cryptoDetailResponse: CryptoDetailResponse?, list: List<FavoriteCrypto>?) =
            CryptoDetail(
                id = cryptoDetailResponse?.id,
                name = cryptoDetailResponse?.name,
                symbol = cryptoDetailResponse?.symbol,
                price = cryptoDetailResponse?.marketData?.currentPrice?.usd ?: 0.0,
                description = cryptoDetailResponse?.description?.tr,
                imageUrl = cryptoDetailResponse?.image?.small,
                hashingAlgorithm = cryptoDetailResponse?.hashingAlgorithm ?: "N/A",
                changeLast24H = cryptoDetailResponse?.marketData?.priceChangePercentage24h,
                isFavorite = list?.map { it.id }?.contains(cryptoDetailResponse?.id)
            )
    }
}