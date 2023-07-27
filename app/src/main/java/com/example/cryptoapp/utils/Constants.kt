package com.example.cryptoapp.utils

object Constants {

    const val BASE_URL = "https://api.coingecko.com/api/v3/"
    const val COINS_LIST = "coins/list"

    const val COINS_BY_ID =
        "coins/{id}?localization=false&tickers=false&market_data=true&community_data=false&developer_data=false&sparkline=false"

    const val COIN_MARKETS =
        "coins/markets?vs_currency=usd&order=market_cap_desc&per_page=30&page=1&sparkline=false"

    const val SYNC_DATA = "syncData"

    const val CHANNEL_ID = "MyTask"
    const val CHANNEL_NAME = "MyTaskChannel"

    const val FAVORITES = "favorites"

    const val COINS = "coins"
}