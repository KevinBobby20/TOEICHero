package com.toeichero.project.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.toeichero.project.Model.shopItem
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_change.*

class ChangeFragment : Fragment() {

    private lateinit var database: FirebaseFirestore
    private lateinit var navController: NavController
    var ownedList: ArrayList<shopItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseFirestore.getInstance()

        navController = Navigation.findNavController(view)

        changeBack.setOnClickListener {
            navController.navigate(R.id.action_changeFragment_to_profileFragment)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.action_changeFragment_to_profileFragment)
                }
            })

        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                val myPfpList = it.get("photoInvent")
                val activePhoto = it.getLong("activePhoto")
                ownedList.add(shopItem(1, R.drawable.main_pfp, "Arcade", 100))
                if(myPfpList.toString().contains("2")){
                    ownedList.add(shopItem(2, R.drawable.monster_pfp, "Monster", 100))
                }
                if(myPfpList.toString().contains("3")){
                    ownedList.add(shopItem(3, R.drawable.robo_pfp, "Robot", 100))
                }
                if(myPfpList.toString().contains("4")){
                    ownedList.add(shopItem(4, R.drawable.disc_pfp, "Group", 100))
                }
                if(myPfpList.toString().contains("5")){
                    ownedList.add(shopItem(5, R.drawable.love_pfp, "Love", 100))
                }
                if(myPfpList.toString().contains("6")){
                    ownedList.add(shopItem(6, R.drawable.moon_pfp, "Moon", 100))
                }
                if(myPfpList.toString().contains("7")){
                    ownedList.add(shopItem(7, R.drawable.cat_pfp, "Cat", 100))
                }
                if(myPfpList.toString().contains("8")){
                    ownedList.add(shopItem(8, R.drawable.axe_pfp, "Axe", 100))
                }
                if(myPfpList.toString().contains("9")){
                    ownedList.add(shopItem(9, R.drawable.game_pfp, "Game", 100))
                }
                changeList.adapter = ChangeAdapter(ownedList, requireActivity(), database, activePhoto!!.toInt())
                changeList.layoutManager = LinearLayoutManager(requireContext())
                changeList.setHasFixedSize(true)
            }
    }

}