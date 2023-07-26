package com.example.cryptoapp.ui.home

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.ui.decorations.BaseVerticalDividerItemDecoration
import com.example.cryptoapp.utils.DeviceUtils
import com.example.cryptoapp.utils.observeData
import com.example.cryptoapp.utils.textChanges
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val MIN_TXT_LENGTH_FOR_SUGGESTION = 2

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

        etSearch.postDelayed({
            etSearch.requestFocus()
            DeviceUtils.openKeyboard(requireActivity(), etSearch)
        }, 100)
        etSearch.setOnEditorActionListener { _, i, keyEvent ->
            if ((i == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) && etSearch.text.toString()
                    .trim().length >= 2
            ) {
                homeViewModel.search(etSearch.text.toString().trim())
            }
            true
        }

        etSearch.textChanges()
            .filterNot { it.isNullOrBlank() }
            .onEach {
                if (it.toString().length >= MIN_TXT_LENGTH_FOR_SUGGESTION) {
                    homeViewModel.search(it.toString())
                } else {
                    homeViewModel.clearSuggestionHistory()
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        etSearch.addTextChangedListener {
            binding.isSearchClearButtonVisible = it.isNullOrEmpty().not()
            if (it.isNullOrEmpty()) homeViewModel.fetchCryptoList()
        }
        ivSearchClear.setOnClickListener {
            etSearch.text?.clear()
            homeViewModel.clearSuggestionHistory()
        }
    }

    private fun onItemClicked(id: String?) {
        navigateTo(HomeFragmentDirections.actionHomeFragmentToDetailFragment(id = id))
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