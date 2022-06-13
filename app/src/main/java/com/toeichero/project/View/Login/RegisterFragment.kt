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
import com.toeichero.project.Model.userAccount
import com.toeichero.project.R
import com.toeichero.project.View.Home.HomeActivity
import com.toeichero.project.View.Onboarding.CarouselActivity
import com.toeichero.project.ViewModel.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: FirebaseFirestore
    private val viewModel: RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        loginNow.setOnClickListener {
            val intent = Intent (getActivity(), LoginActivity::class.java)
            intent.putExtra("Status", 2)
            getActivity()?.startActivity(intent)
        }

        RegisterBtn.setOnClickListener {
            var photoList: ArrayList<Int> = ArrayList()
            photoList.add(1)
            val userAccount = userAccount(usernameBox.text.toString(), emailBox.text.toString().trim(), passBox.text.toString().trim(), 1, photoList, 0, 250, photoList, "False", 1, 1, 0)
            viewModel.validateRegis(usernameBox.text.toString(), emailBox.text.toString().trim(), passBox.text.toString().trim(), mAuth, requireActivity(), database, userAccount)
            viewModel.isLoading.observe(viewLifecycleOwner, Observer { regisStatus ->
                if(regisStatus){
                    viewModel.mutableIsLoading.value = false
                    Toast.makeText(requireActivity(),"Register Success", Toast.LENGTH_SHORT).show();
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