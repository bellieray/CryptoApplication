package com.example.data.database

import android.content.Context
import androidx.room.Room
import com.example.data.datasource.local.CoinsDAO
import com.example.data.datasource.local.CryptoDB
import com.example.domain.model.Crypto
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

class DaoTest {
    private lateinit var cryptoDatabase: CryptoDB
    private lateinit var dao: CoinsDAO

    @Mock
    lateinit var mockContext: Context

    @Before
    fun setup() {
        cryptoDatabase = Room.inMemoryDatabaseBuilder(
            mockContext,
            CryptoDB::class.java
        ).allowMainThreadQueries().build()

        dao = cryptoDatabase.coinsDAO()
    }

    @After
    fun teardown() {
        cryptoDatabase.close()
    }

    @Test
    fun insertAll() = runBlocking {
        val list: MutableList<Crypto> = mutableListOf()
        val crypto = Crypto("1", "Tr", "Bittorrent")
        list.add(crypto)
        dao.insertAll(list)

        val observedList = dao.getAll()
        assertThat(observedList).contains(crypto)
    }

    @Test
    fun getAll() = runBlocking {
        val list: MutableList<Crypto> = mutableListOf()
        val crypto = Crypto("1", "Tr", "Bittorrent")
        list.add(crypto)
        dao.insertAll(list)

        val observedList = dao.getAll()
        assertThat(observedList.contains(Crypto("1", "Tr", "Bittorrent"))).isTrue()
    }

    @Test
    fun searchItem() = runBlocking {
        val list: MutableList<Crypto> = mutableListOf()
        list.add(Crypto("1", "Tr", "Bittorrent"))
        list.add(Crypto("2", "Tr", "Bitcoin"))
        list.add(Crypto("3", "Tr", "Etherium"))
        list.add(Crypto("4", "Tr", "FbCoin"))
        dao.insertAll(list)
        delay(500)
        val searchList = dao.search("bit")
        assertThat(
            searchList.containsAll(
                arrayListOf(
                    Crypto("1", "Tr", "Bittorrent"),
                    Crypto("2", "Tr", "Bitcoin")
                )
            )
        ).isTrue()
    }
}