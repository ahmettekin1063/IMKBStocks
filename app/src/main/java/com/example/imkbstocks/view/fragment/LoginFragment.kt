package com.example.imkbstocks.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.imkbstocks.HandshakeStatus
import com.example.imkbstocks.R
import com.example.imkbstocks.databinding.FragmentLoginBinding
import com.example.imkbstocks.util.showErrorMessage
import com.example.imkbstocks.viewmodel.LoginViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.fragment = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        viewModel.handshake()
        observeHandshakeStatus()
    }

    private fun observeHandshakeStatus(){
        viewModel.handshakeStatus.observe(viewLifecycleOwner, { handshakeStatus ->
            handshakeStatus?.let{
                binding.btnLogin.visibility = it.btnLoginVisibility
                binding.pbHandshake.visibility = it.pbHandshakeVisibility
                if (handshakeStatus == HandshakeStatus.FAILURE) showErrorMessage(requireContext())
            }
        })
    }

    fun onLoginClicked(view: View){
        goToStocksFragment(view)
    }

    private fun goToStocksFragment(view: View) {
        val action = LoginFragmentDirections.actionLoginFragmentToStocksFragment()
        Navigation.findNavController(view).navigate(action)
    }

}