package com.example.cryptoapp.ui.detail

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.ui.detail.dialog.RefreshTimeBottomSheetDialog
import com.example.cryptoapp.ui.favorite.FavoriteViewModel
import com.example.cryptoapp.utils.observeData
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()
    private var refreshTimeBottomSheetDialog: RefreshTimeBottomSheetDialog? = null

    override fun initViews() = with(binding) {
        icArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
        ivFavorite.setOnClickListener {
            if (detailViewModel.detailViewState.value.cryptoDetail?.isFavorite == true) {
                detailViewModel.detailViewState.value.cryptoDetail?.id?.let { safeId ->
                    detailViewModel.removeFromFavorite(
                        safeId
                    )
                    favoriteViewModel.fetchFavorites()
                }
            } else {
                detailViewModel.addToFavorite(detailViewModel.detailViewState.value.cryptoDetail)
                favoriteViewModel.fetchFavorites()
            }
        }

        btnRefresh.setOnClickListener {
            if (refreshTimeBottomSheetDialog == null) {
                refreshTimeBottomSheetDialog = RefreshTimeBottomSheetDialog({
                    refreshTimeBottomSheetDialog = null
                }, {
                    detailViewModel.changeRefreshTime(it)
                })
                refreshTimeBottomSheetDialog?.show(
                    childFragmentManager,
                    RefreshTimeBottomSheetDialog::class.java.simpleName
                )
            }

        }
    }

    override fun initObservers() {
        observeData {
            detailViewModel.detailViewState.collect { state ->
                state.consumableErrors?.firstOrNull()?.let { error ->
                    notify(error.exception)
                    detailViewModel.errorConsumed(error.id)
                }

                state.isLoading?.let { isLoad ->
                    binding.isLoading = isLoad
                }

                state.cryptoDetail?.let { safeModel ->
                    binding.crpytoDetail = safeModel
                }

                state.currentPrice?.let { currentPrice ->
                    binding.currentPrice = currentPrice
                }

                state.detailEvents?.firstOrNull()?.let { event ->
                    when (event) {
                        is DetailEvent.ShowCompleteMessage -> {
                            notify(event.completeText)
                            detailViewModel.eventConsumed(event)
                        }
                    }
                }

            }
        }

        observeData {
            favoriteViewModel.favoriteViewState.collect {
                it.favoriteList?.let { list ->
                    detailViewModel.setFavoriteList(list)
                }
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_detail
}