package com.feature.home

import com.example.domain.model.Crypto
import com.example.domain.model.Result
import com.example.domain.usecase.SearchUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class HomeViewModelTest {
    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var fakeGetAllCryptosUseCase: FakeGetAllCryptosUseCase

    @Mock
    private lateinit var fakeInsertListToDbUseCase: FakeInsertListToDbUseCase

    @Mock
    private lateinit var fakeSearchUseCase: SearchUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        homeViewModel =
            HomeViewModel(fakeGetAllCryptosUseCase, fakeInsertListToDbUseCase, fakeSearchUseCase)
    }

    @Test
    fun `fetch crypto list error case`() = runTest {
        Mockito.`when`(
            fakeGetAllCryptosUseCase()
        ).thenReturn(Result.Failed("error"))

        // Act
        homeViewModel.fetchCryptoList()

        // Assert
        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.isLoading).isEqualTo(false)
        Truth.assertThat(homeViewState.consumableErrors?.first()?.exception).isEqualTo("error")
    }

    @Test
    fun `fetch crypto list success case`() = runTest {
        Mockito.`when`(
            fakeGetAllCryptosUseCase()
        ).thenReturn(Result.Success(cryptoCurrencies))

        // Act
        homeViewModel.fetchCryptoList()

        // Assert
        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.isLoading).isEqualTo(false)
        Truth.assertThat(homeViewState.cryptoList).isEqualTo(cryptoCurrencies)
    }


    @Test
    fun `insertItemToDb success test`() = runTest {
        `when`(fakeInsertListToDbUseCase.invoke(cryptoCurrencies)).thenReturn(Result.Success(Unit))

        homeViewModel.insertItemToDb(cryptoCurrencies)

        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.isLoading).isEqualTo(false)
    }


    @Test
    fun `insertItemToDb failure test`() = runTest {
        `when`(fakeInsertListToDbUseCase.invoke(cryptoCurrencies)).thenReturn(Result.Failed("Error"))

        homeViewModel.insertItemToDb(cryptoCurrencies)

        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.isLoading).isEqualTo(false)
        Truth.assertThat(homeViewState.consumableErrors?.first()?.exception).isEqualTo("Error")
    }


    @Test
    fun `search success test`() = runTest {
        val searchText = "Bit"
        val list: MutableList<Crypto> = mutableListOf()
        list.add(Crypto("1", "Tr", "Bittorrent"))
        list.add(Crypto("2", "Tr", "Bitcoin"))
        list.add(Crypto("3", "Tr", "Etherium"))
        list.add(Crypto("4", "Tr", "FbCoin"))

        `when`(fakeSearchUseCase.invoke(searchText)).thenReturn(Result.Success(list))

        homeViewModel.search(searchText)

        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.isLoading).isEqualTo(false)
        // Verify that searchResultList was updated with mockSearchResult

        // Verify that error list is empty
        Truth.assertThat(
            homeViewState.searchResultList
        ).contains(Crypto("2", "Tr", "Bitcoin"))
    }


    @Test
    fun `search failure test`() = runTest {
        val searchText = "Bit"
        val list: MutableList<Crypto> = mutableListOf()
        list.add(Crypto("1", "Tr", "Bittorrent"))
        list.add(Crypto("2", "Tr", "Bitcoin"))
        list.add(Crypto("3", "Tr", "Etherium"))
        list.add(Crypto("4", "Tr", "FbCoin"))

        `when`(fakeSearchUseCase.invoke(searchText)).thenReturn(Result.Failed("Error"))
        homeViewModel.search(searchText)

        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.isLoading).isEqualTo(false)
        Truth.assertThat(homeViewState.consumableErrors?.first()?.exception).isEqualTo("Error")
    }


    @Test
    fun `errorConsumed removes error from list`() = runTest {
        // Arrange
        homeViewModel.addErrorToList("errorId")
        homeViewModel.errorConsumed(
            homeViewModel.homeViewState.value.consumableErrors?.first()?.id ?: 0
        )

        // Assert
        val homeViewState = homeViewModel.homeViewState.value
        Truth.assertThat(homeViewState.consumableErrors).isEmpty()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}