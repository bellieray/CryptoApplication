package com.example.cryptoapp.ui.favorite

import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentFavoriteBinding
import com.example.cryptoapp.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {
    override fun initViews() {

    }

    override fun getFragmentView() = R.layout.fragment_favorite

}