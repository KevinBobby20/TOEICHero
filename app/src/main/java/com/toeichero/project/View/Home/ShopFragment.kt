package com.toeichero.project.View.Home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.toeichero.project.Model.shopItem
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_shop.*

class ShopFragment : Fragment() {

    private lateinit var database: FirebaseFirestore
    private lateinit var navController: NavController
    var list: ArrayList<shopItem> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseFirestore.getInstance()

        navController = Navigation.findNavController(view)

        shopBack.setOnClickListener {
            navController.navigateUp()
        }

        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                textMoneyShop.text = it.getLong("money").toString()
                val myPfpList = it.get("photoInvent")
                Log.e("List", myPfpList.toString())
                Log.e("List", list.toString())
                if(!myPfpList.toString().contains("2")){
                    list.add(shopItem(2, R.drawable.monster_pfp, "Monster", 100))
                }
                if(!myPfpList.toString().contains("3")){
                    list.add(shopItem(3, R.drawable.robo_pfp, "Robot", 100))
                }
                if(!myPfpList.toString().contains("4")){
                    list.add(shopItem(4, R.drawable.disc_pfp, "Group", 100))
                }
                if(!myPfpList.toString().contains("5")){
                    list.add(shopItem(5, R.drawable.love_pfp, "Love", 100))
                }
                if(!myPfpList.toString().contains("6")){
                    list.add(shopItem(6, R.drawable.moon_pfp, "Moon", 100))
                }
                if(!myPfpList.toString().contains("7")){
                    list.add(shopItem(7, R.drawable.cat_pfp, "Cat", 100))
                }
                if(!myPfpList.toString().contains("8")){
                    list.add(shopItem(8, R.drawable.axe_pfp, "Axe", 100))
                }
                if(!myPfpList.toString().contains("9")){
                    list.add(shopItem(9, R.drawable.game_pfp, "Game", 100))
                }
                Log.e("List", list.toString())
                shopList.adapter = ShopAdapter(list, requireActivity(), database, textMoneyShop)
                shopList.layoutManager = LinearLayoutManager(requireContext())
                shopList.setHasFixedSize(true)
            }

    }

}