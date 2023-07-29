package com.feature.login.sign_in

import androidx.fragment.app.viewModels
import com.core.common.base.BaseFragment
import com.core.common.utils.DeviceUtils
import com.core.common.utils.StringUtils
import com.core.common.utils.VALIDATION
import com.core.common.utils.observeData
import com.feature.login.R
import com.feature.login.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding>() {
    private val signInViewModel by viewModels<SignInViewModel>()
    override fun initViews() = with(binding) {
        root.setOnClickListener {
            DeviceUtils.closeKeyboard(requireActivity(), binding.root)
        }
        etEmail.requestFocus()
        etEmail.postDelayed({
            DeviceUtils.openKeyboard(
                requireActivity(),
                etEmail,
                null
            )
        }, 400)

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
                            navigateTo(SignInFragmentDirections.actionSignInFragmentToHomeNavGraph())
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