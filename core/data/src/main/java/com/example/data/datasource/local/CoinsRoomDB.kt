package com.example.cryptoapp.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.datasource.local.CoinsDAO
import com.example.domain.model.Crypto

@Database(
    entities = [Crypto::class],
    version = 1,
    exportSchema = false
)
abstract class CryptoDB : RoomDatabase() {
    abstract fun coinsDAO(): CoinsDAO
}