package com.feature.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.core.common.base.BaseFragment
import com.core.common.decorations.BaseVerticalDividerItemDecoration
import com.core.common.utils.DeviceUtils
import com.core.common.utils.observeData
import com.core.common.utils.textChanges
import com.feature.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val MIN_TXT_LENGTH_FOR_SUGGESTION = 2
private const val KEYBOARD_DELAY = 200L

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val homeViewModel by viewModels<HomeViewModel>()
    lateinit var homeListAdapter: HomeListAdapter

    private val activityResultLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted.not()) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                    notify("Permission denied please go settings")
                } else {
                    notify("Permission denied please go settings")
                }
            }
        }


    override fun initViews() = with(binding) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                activityResultLauncher.launch(
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }

        }

        homeViewModel.fetchCryptoList()
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
        }, KEYBOARD_DELAY)
        etSearch.setOnEditorActionListener { _, i, keyEvent ->
            if ((i == EditorInfo.IME_ACTION_SEARCH || (keyEvent != null && keyEvent.keyCode == KeyEvent.KEYCODE_ENTER)) && etSearch.text.toString()
                    .trim().length >= 2
            ) {
                homeViewModel.search(etSearch.text.toString().trim())
            }
            true
        }

        etSearch.textChanges().filterNot { it.isNullOrBlank() }.onEach {
            if (it.toString().length >= MIN_TXT_LENGTH_FOR_SUGGESTION) {
                homeViewModel.search(it.toString())
            } else homeViewModel.clearSuggestionHistory()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        etSearch.addTextChangedListener {
            binding.isSearchClearButtonVisible = it.isNullOrEmpty().not()
        }
        ivSearchClear.setOnClickListener {
            etSearch.text?.clear()
        }
    }

    private fun onItemClicked(id: String?) {
        navigateTo(HomeFragmentDirections.homeToDetail(id))
    }

    override fun initObservers() {
        observeData {
            homeViewModel.homeViewState.collect {
                it.cryptoList?.let { cryptos ->
                    homeListAdapter.submitList(cryptos)
                }

                binding.isLoading = it.isLoading

                it.searchResultList?.let { searchResult ->
                    homeListAdapter.submitList(searchResult)
                }

                it.consumableErrors?.firstOrNull()?.let { error ->
                    notify(error.exception)
                    homeViewModel.errorConsumed(error.id)
                }
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_home
}
