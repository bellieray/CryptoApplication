package com.example.cryptoapp.ui.login.sign_in

import androidx.fragment.app.viewModels
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.utils.DeviceUtils
import com.example.cryptoapp.utils.StringUtils
import com.example.cryptoapp.utils.VALIDATION
import com.example.cryptoapp.utils.observeData
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    private val signInViewModel by viewModels<SignInViewModel>()
    override fun initViews() = with(binding) {
        root.setOnClickListener {
            DeviceUtils.closeKeyboard(requireActivity(), binding.root)
        }
        btnSignIn.setOnClickListener {
            if (StringUtils.isEmailValid(etEmail.text.toString()) == VALIDATION.VALID && StringUtils.isPasswordValid(
                    etPassword.text.toString()
                ) == VALIDATION.VALID
            ) {
                signInViewModel.login(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            } else {
                notify("Please check your mail adress or password")
            }
        }
        tvRegister.setOnClickListener {
            navigateTo(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
    }

    override fun initObservers() {
        observeData {
            signInViewModel.signInViewState.collect {
                it.signInEvents?.firstOrNull()?.let { event ->
                    when (event) {
                        is SignInEvent.NavigateToHome -> {
                            navigateTo(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
                            signInViewModel.eventConsumed(event)
                        }
                    }
                }

                it.consumableErrors?.firstOrNull()?.let { consumable ->
                    consumable.exception.let { error ->
                        notify(error)
                        signInViewModel.errorConsumed(consumable.id)
                    }
                }

                it.isLoading?.let { isLoading ->
                    binding.isLoading = isLoading
                }
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_sign_in

}