package com.toeichero.project.View.Gameplay

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import com.toeichero.project.View.Home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toeichero.project.R
import kotlinx.android.synthetic.main.fragment_end_game.*


class EndGameFragment : Fragment() {

    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_end_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseFirestore.getInstance()
        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                val score = it.getLong("tempScore").toString()
                scoreTemp.text = score
            }
        backMenuBtn.setOnClickListener {
            val intent = Intent (getActivity(), HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            getActivity()?.startActivity(intent)
        }
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Handler().postDelayed({
                        val intent = Intent (getActivity(), HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        getActivity()?.startActivity(intent)
                    }, 600)
                }
            })
    }
}