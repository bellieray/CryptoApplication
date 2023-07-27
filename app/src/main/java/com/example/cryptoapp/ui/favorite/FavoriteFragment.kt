package com.example.cryptoapp.ui.favorite

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.ui.decorations.BaseVerticalDividerItemDecoration
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentFavoriteBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        navigateTo(FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(id = id))
    }

    override fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
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