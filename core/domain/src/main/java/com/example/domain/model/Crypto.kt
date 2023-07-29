package com.example.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("crypto_list")
data class Crypto(
    @ColumnInfo(name = "id")
    @PrimaryKey val id: String,
    @ColumnInfo(name = "symbol")
    val symbol: String?,
    @ColumnInfo(name = "name")
    val name: String?
)