package com.example.diplomaproject.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomaproject.R
import com.example.diplomaproject.core.repository.SharedPreferencesRepo
import com.example.diplomaproject.core.state.RegistrationState
import com.example.diplomaproject.core.state.VerificationState
import com.example.diplomaproject.databinding.FragmentVerificationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val REG_USER_NAME = "reg_user_name"
const val REG_USER_PASSWORD = "reg_user_pass"

@AndroidEntryPoint
class VerificationFragment : Fragment(R.layout.fragment_verification) {

    @Inject lateinit var sharedPreferencesRepo: SharedPreferencesRepo
    private val viewBinding: FragmentVerificationBinding by viewBinding()
    private val viewModel: RegisterViewModel by viewModels()

    private lateinit var username: String
    private lateinit var password: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = requireArguments().getString(REG_USER_NAME)!!
        password = requireArguments().getString(REG_USER_PASSWORD)!!

        initActions()
        initObserver()

        viewModel.sendEmailVerification(
            username = username,
            smsRequestType = "REGISTER"
        )
    }

    private fun initActions() = with(viewBinding) {
        buttonVerifyAccount.setOnClickListener {
            val verificationCode = fieldCode.text.toString()

            if (verificationCode.isNotEmpty()) {
                viewModel.signUp(
                    username = username,
                    password = password,
                    verificationCode = verificationCode
                )
            }
        }
    }

    private fun initObserver() {
        viewModel.registrationStateLiveData.observe(viewLifecycleOwner, ::handleRegistrationState)
        viewModel.verificationStateLiveData.observe(viewLifecycleOwner, ::handleSendingEmailVerification)
    }

    private fun handleSendingEmailVerification(state: VerificationState) {
        when (state) {
            is VerificationState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is VerificationState.Loading -> {}
            is VerificationState.Success -> {
                Toast.makeText(requireContext(), "Verification mail sent", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleRegistrationState(state: RegistrationState) {
        when (state) {
            is RegistrationState.Failed -> {
                Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
            }
            is RegistrationState.Loading -> {}
            is RegistrationState.Success -> {
                if (state.message == "CREATED") {
                    Toast.makeText(
                        requireContext(),
                        "Succesful Registration",
                        Toast.LENGTH_SHORT
                    ).show()

                    sharedPreferencesRepo.setUserEmail(username)
                    findNavController().navigate(R.id.action_verificationFragment_to_homeActivity)
                }
            }
        }
    }

}