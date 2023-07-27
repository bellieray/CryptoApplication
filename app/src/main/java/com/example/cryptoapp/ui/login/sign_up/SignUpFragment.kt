package com.example.cryptoapp.ui.login.sign_up

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.ui.base.BaseFragment
import com.example.cryptoapp.utils.DeviceUtils
import com.example.cryptoapp.utils.StringUtils
import com.example.cryptoapp.utils.VALIDATION
import com.example.cryptoapp.utils.observeData
import com.example.cryptocurrency.R
import com.example.cryptocurrency.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding>() {
    private val signUpViewModel by viewModels<SignUpViewModel>()
    override fun initViews() = with(binding) {
        etEmail.requestFocus()
        etEmail.postDelayed({
            DeviceUtils.openKeyboard(requireActivity(), etEmail, null)
        }, 400)

        btnSignUp.setOnClickListener {
            if (StringUtils.isEmailValid(etEmail.text.toString()) == VALIDATION.VALID && StringUtils.isPasswordValid(
                    etPassword.text.toString()
                ) == VALIDATION.VALID
            ) {
                signUpViewModel.register(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please check your email adress or password",
                    Toast.LENGTH_LONG
                ).show()
            }

        }
        ivArrowBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initObservers() {
        observeData {
            signUpViewModel.signUpViewState.collect {
                it.signUpEvents?.firstOrNull()?.let { event ->
                    when (event) {
                        is SignUpEvent.NavigateToSignIn -> {
                            navigateTo(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
                            notify("User created successfully")
                            signUpViewModel.eventConsumed(event)
                        }
                    }
                }

                it.consumableErrors?.firstOrNull()?.let { error ->
                    notify(error.exception)
                    signUpViewModel.errorConsumed(error.id)
                }


                it.isLoading?.let { isLoading ->
                    binding.isLoading = isLoading
                }
            }
        }
    }

    override fun getFragmentView() = R.layout.fragment_sign_up
}