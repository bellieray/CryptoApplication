package com.example.cryptoapp.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.model.Crypto

@Dao
interface CoinsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(infos: List<Crypto>)

    @Query("SELECT * FROM crypto_list WHERE symbol LIKE '%' || :word || '%' OR name LIKE '%' || :word")
    suspend fun search(word: String): List<Crypto>

    @Query("SELECT * FROM crypto_list")
    suspend fun getAll(): List<Crypto>
}