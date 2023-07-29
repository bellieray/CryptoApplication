package com.example.data.di

import android.app.Application
import androidx.room.Room
import com.example.data.datasource.local.CoinsDAO
import com.example.cryptoapp.data.datasource.local.CryptoDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideCryptoDB(app: Application): CryptoDB {
        return Room.databaseBuilder(
            app, CryptoDB::class.java, "coin_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCoinDao(cryptoDB: CryptoDB): CoinsDAO {
        return cryptoDB.coinsDAO()
    }
}