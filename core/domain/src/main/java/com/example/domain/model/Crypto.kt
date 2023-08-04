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
){
    // Override the equals method to compare the content of Crypto objects
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Crypto) return false
        return id == other.id && symbol == other.symbol && name == other.name
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}