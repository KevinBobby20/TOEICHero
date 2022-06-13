package com.toeichero.project.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toeichero.project.R
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        categoryBack.setOnClickListener {
            navController.navigateUp()
        }

        listeningBtn.setOnClickListener {
            navController.navigate(R.id.action_categoryFragment_to_listenSectionFragment)
        }

        readingBtn.setOnClickListener {
            navController.navigate(R.id.action_categoryFragment_to_readSectionFragment)
        }

    }
}