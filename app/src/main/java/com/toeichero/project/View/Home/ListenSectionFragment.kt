package com.toeichero.project.View.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toeichero.project.R
import com.toeichero.project.View.Gameplay.GameplayActivity
import kotlinx.android.synthetic.main.fragment_listen_section.*
import kotlinx.android.synthetic.main.fragment_read_section.*

class ListenSectionFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_listen_section, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        listenSecBack.setOnClickListener {
            navController.navigateUp()
        }

        QuestionAndResponse.setOnClickListener {
            val intent = Intent (getActivity(), GameplayActivity::class.java)
            intent.putExtra("Status", 2)
            intent.putExtra("Type", 2)
            getActivity()?.startActivity(intent)
        }

        Conversations.setOnClickListener {
            val intent = Intent (getActivity(), GameplayActivity::class.java)
            intent.putExtra("Status", 2)
            intent.putExtra("Type", 3)
            getActivity()?.startActivity(intent)
        }

        Talks.setOnClickListener {
            val intent = Intent (getActivity(), GameplayActivity::class.java)
            intent.putExtra("Status", 2)
            intent.putExtra("Type", 4)
            getActivity()?.startActivity(intent)
        }

        photographs.setOnClickListener {
            val intent = Intent (getActivity(), GameplayActivity::class.java)
            intent.putExtra("Status", 2)
            intent.putExtra("Type", 1)
            getActivity()?.startActivity(intent)
        }
    }

}