package com.feature.favorite

import androidx.fragment.app.activityViewModels
import com.core.common.utils.observeData
import com.core.common.base.BaseFragment
import com.core.common.decorations.BaseVerticalDividerItemDecoration
import com.core.common.viewmodel.FavoriteViewModel
import com.feature.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    private val favoriteViewModel by activityViewModels<FavoriteViewModel>()
    private val favoriteAdapter: FavoriteAdapter by lazy {
        FavoriteAdapter(this@FavoriteFragment::onItemClicked)
    }

    override fun initViews() = with(binding) {
        favoriteViewModel.fetchFavorites()
        rvFavorites.adapter = favoriteAdapter
        rvFavorites.addItemDecoration(
            BaseVerticalDividerItemDecoration(
                requireContext(),
                paddingInResId = R.dimen.margin_10,
                paddingOutResId = R.dimen.margin_20
            )
        )
    }

    private fun onItemClicked(id: String?) {
        navigateTo(FavoriteFragmentDirections.favoriteToDetail(id = id))
    }

    override fun initObservers() {
        observeData {
            favoriteViewModel.favoriteViewState.collect { state ->
                state.consumableErrors?.firstOrNull()?.let {
                    notify(it.exception)
                    favoriteViewModel.errorConsumed(it.id)
                }

                state.favoriteList?.let { safeList ->
                    favoriteAdapter.submitList(safeList)
                    binding.isHasData = safeList.isNotEmpty()
                }

                binding.isLoading = state.isLoading
            }
        }

    }

    override fun getFragmentView() = R.layout.fragment_favorite

}