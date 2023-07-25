package com.example.cryptoapp.ui.detail

import androidx.fragment.app.viewModels
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.utils.observeData
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {
    private val detailViewModel by viewModels<DetailViewModel>()
    override fun initViews() = with(binding) {

    }

    override fun initObservers() {
        observeData {
            detailViewModel.detailViewState.collect { state ->
                state.consumableErrors?.firstOrNull()?.let { error ->
                    notify(error.exception)
                    detailViewModel.errorConsumed(error.id)
                }

            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_detail
}