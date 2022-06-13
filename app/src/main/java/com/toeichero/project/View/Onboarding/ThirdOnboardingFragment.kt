package com.toeichero.project.View.Onboarding

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toeichero.project.R
import com.toeichero.project.View.Login.LoginActivity
import com.toeichero.project.View.Login.RegisterActivity
import kotlinx.android.synthetic.main.fragment_third_onboarding.*

class ThirdOnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBtn.setOnClickListener {
            val intent = Intent (getActivity(), LoginActivity::class.java)
            getActivity()?.startActivity(intent)
        }

        regisBtn.setOnClickListener {
            val intent = Intent (getActivity(), RegisterActivity::class.java)
            getActivity()?.startActivity(intent)
        }

    }

}