package com.feature.detail

import androidx.lifecycle.SavedStateHandle
import com.example.domain.usecase.AddToFavoriteUseCase
import com.example.domain.usecase.GetCryptoDetailUseCase
import com.example.domain.usecase.GetPriceUseCase
import com.example.domain.usecase.RemoveFromFavoriteUseCase
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DetailViewModelTest {
    @Mock
    private lateinit var mockGetCryptoDetailUseCase: GetCryptoDetailUseCase

    @Mock
    private lateinit var fakeGetPriceUseCase: FakeGetPriceUseCase

    @Mock
    private lateinit var mockAddToFavoriteUseCase: AddToFavoriteUseCase

    @Mock
    private lateinit var mockRemoveFromFavoriteUseCase: RemoveFromFavoriteUseCase

    private lateinit var detailViewModel: DetailViewModel


    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        detailViewModel = DetailViewModel(
            mockGetCryptoDetailUseCase,
            fakeGetPriceUseCase,
            mockAddToFavoriteUseCase,
            mockRemoveFromFavoriteUseCase,
            SavedStateHandle()
        )

    }

    @Test
    fun `addToFavorite success test`() = runTest {
        whenever(mockAddToFavoriteUseCase.invoke(cryptoDetail))
            .thenReturn(com.example.domain.model.Result.Success(Unit))

        detailViewModel.addToFavorite(cryptoDetail)

        verify(mockAddToFavoriteUseCase).invoke(cryptoDetail)
        val detailViewState = detailViewModel.detailViewState.value
        // Truth.assertThat(detailViewState.isLoading).isEqualTo(false)
        Truth.assertThat(detailViewState.detailEvents)
            .containsExactly(DetailEvent.ShowCompleteMessage("Successfully Added"))
    }

    @Test
    fun `addToFavorite error test`() = runTest {
        whenever(mockAddToFavoriteUseCase.invoke(cryptoDetail))
            .thenReturn(com.example.domain.model.Result.Failed("Error"))

        detailViewModel.addToFavorite(cryptoDetail)

        verify(mockAddToFavoriteUseCase).invoke(cryptoDetail)
        val detailViewState = detailViewModel.detailViewState.value
     //   Truth.assertThat(detailViewState.isLoading).isEqualTo(false)
        Truth.assertThat(detailViewState.consumableErrors?.first()?.exception).isEqualTo("error")
    }


    @Test
    fun `removeFromFavorite success test`() = runTest {
        val coinId = "bitcoin"
        whenever(mockRemoveFromFavoriteUseCase.invoke(coinId))
            .thenReturn(com.example.domain.model.Result.Success(Unit))

        detailViewModel.removeFromFavorite(coinId)

        verify(mockRemoveFromFavoriteUseCase).invoke(coinId)
        val detailViewState = detailViewModel.detailViewState.value
        // Truth.assertThat(detailViewState.isLoading).isEqualTo(false)
        Truth.assertThat(detailViewState.detailEvents)
            .containsExactly(DetailEvent.ShowCompleteMessage("Successfully Removed"))
    }

    @Test
    fun `removeFromFavorite error test`() = runTest {
        val coinId = "bitcoin"
        whenever(mockRemoveFromFavoriteUseCase.invoke(coinId))
            .thenReturn(com.example.domain.model.Result.Failed("Error"))

        detailViewModel.removeFromFavorite(coinId)

        verify(mockRemoveFromFavoriteUseCase).invoke(coinId)
        val detailViewState = detailViewModel.detailViewState.value
        //   Truth.assertThat(detailViewState.isLoading).isEqualTo(false)
        Truth.assertThat(detailViewState.consumableErrors?.first()?.exception).isEqualTo("Error")
    }
    @Test
    fun `changeRefreshTime success test`() = runTest {
        val coinId = "bitcoin"
        val time = "5"
        val mockResponse = com.example.domain.model.Result.Success(123.45)
        whenever(fakeGetPriceUseCase.invoke(coinId, time))
            .thenReturn(mockResponse)

        detailViewModel.changeRefreshTime(time)

        verify(fakeGetPriceUseCase).invoke(coinId, time)
        Truth.assertThat(detailViewModel.detailViewState.value.currentPrice).isEqualTo(123.45)
    }
}