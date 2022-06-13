package com.toeichero.project.View.Login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.toeichero.project.R
import com.toeichero.project.View.Home.HomeActivity
import com.toeichero.project.View.Onboarding.CarouselActivity
import com.toeichero.project.ViewModel.LoginViewModel
import com.toeichero.project.ViewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_third_onboarding.*

class LoginFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()

        createAcc.setOnClickListener {
            val intent = Intent (getActivity(), RegisterActivity::class.java)
            intent.putExtra("Status", 2)
            getActivity()?.startActivity(intent)
        }

        loginButton.setOnClickListener {
            viewModel.validateLogin(emailField.text.toString().trim(), passField.text.toString().trim(), mAuth, requireActivity())
            viewModel.isLoading.observe(viewLifecycleOwner, Observer { loginStatus ->
                if(loginStatus){
                    viewModel.mutableIsLoading.value = false
                    Toast.makeText(requireActivity(),"Login Success", Toast.LENGTH_SHORT).show();
                    val intent = Intent (getActivity(), HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    getActivity()?.startActivity(intent)
                }
            })
        }

        val status = requireActivity().intent.getIntExtra("Status",0)
        if (status == 2){
            requireActivity()
                .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        val intent = Intent (getActivity(), CarouselActivity::class.java)
                        intent.putExtra("Status", 1)
                        getActivity()?.startActivity(intent)
                    }
                })
        }

    }

}