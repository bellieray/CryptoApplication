package com.example.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.domain.model.Crypto

@Dao
interface CoinsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(infos: List<Crypto>)

    @Query("SELECT * FROM crypto_list WHERE LOWER(name) LIKE '%' || LOWER(:word) || '%' OR LOWER(symbol) LIKE '%' || LOWER(:word) || '%'")
    suspend fun search(word: String): List<Crypto>

    @Query("SELECT * FROM crypto_list")
    suspend fun getAll(): List<Crypto>
}