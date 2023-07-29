package com.example.domain.model

import com.google.firebase.firestore.QuerySnapshot

data class FavoriteCrypto(
    val id: String?,
    val name: String?,
    val symbol: String?,
    val price: Double?,
    val imageUrl: String?,
    val changeLast24H: Double?
) {
    companion object {
        fun from(qs: QuerySnapshot) =
            qs.documents.map {
                FavoriteCrypto(
                    id = it.get("id") as? String,
                    name = it.get("name") as? String,
                    symbol = it.get("symbol") as? String,
                    price = it.get("price") as? Double,
                    imageUrl = it.get("imageUrl") as? String?,
                    changeLast24H = it.get("changeLast24H") as? Double
                )
            }
    }
}