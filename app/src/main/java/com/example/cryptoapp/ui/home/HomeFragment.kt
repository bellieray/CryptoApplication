package com.example.cryptocurrency.ui.home

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentHomeBinding
import com.example.cryptocurrency.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override fun initViews() = with(binding) {
    }

    override fun initObservers() {}
    override fun getFragmentView() = R.layout.fragment_home
}