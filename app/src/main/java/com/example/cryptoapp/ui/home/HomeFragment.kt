package com.example.cryptoapp.ui.home

import androidx.fragment.app.viewModels
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.ui.decorations.BaseVerticalDividerItemDecoration
import com.example.cryptoapp.utils.observeData
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel by viewModels<HomeViewModel>()
    lateinit var homeListAdapter: HomeListAdapter
    override fun initViews() = with(binding) {
        homeListAdapter = HomeListAdapter(this@HomeFragment::onItemClicked)
        rvHome.adapter = homeListAdapter
        rvHome.addItemDecoration(
            BaseVerticalDividerItemDecoration(
                requireContext(),
                paddingInResId = R.dimen.margin_10,
                paddingOutResId = R.dimen.margin_20
            )
        )

    }

    private fun onItemClicked() {

    }


    override fun initObservers() {
        observeData {
            homeViewModel.homeViewState.collect {
                it.cryptoList?.let { cryptos ->
                    homeListAdapter.submitList(cryptos)
                }
                it.isLoading?.let { isLoading ->
                    binding.isLoading = isLoading
                }
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_home
}