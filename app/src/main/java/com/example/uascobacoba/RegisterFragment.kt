package com.example.uascobacoba

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.uascobacoba.databinding.FragmentRegisterBinding


class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = MainActivity.getInstance()!!

        with(binding) {
            btRegister.setOnClickListener {
                mainActivity.registerAccount(
                    nama = etRegisterFullname.text.toString(),
                    nim = etRegisterNim.text.toString(),
                    username = etRegisterUser.text.toString(),
                    email = etRegisterEmail.text.toString(),
                    password = etRegisterPassword.text.toString()
                )
            }
        }
    }

}