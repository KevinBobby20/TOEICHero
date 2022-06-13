package com.toeichero.project.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_home.*

import smartdevelop.ir.eram.showcaseviewlib.GuideView

import smartdevelop.ir.eram.showcaseviewlib.config.DismissType


class HomeFragment : Fragment() {

    private lateinit var database: FirebaseFirestore
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseFirestore.getInstance()

        navController = Navigation.findNavController(view)

        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                val picCode = it.getLong("activePhoto").toString()
                textMoney.text = it.getLong("money").toString()
                namaUser.text = it.getString("username")
                val tutorialCode = it.getString("tutorial")
                if(picCode == "1"){
                    logoProfile.setBackgroundResource(R.drawable.main_pfp)
                }else if(picCode == "2"){
                    logoProfile.setBackgroundResource(R.drawable.monster_pfp)
                }else if(picCode == "3"){
                    logoProfile.setBackgroundResource(R.drawable.robo_pfp)
                }else if(picCode == "4"){
                    logoProfile.setBackgroundResource(R.drawable.disc_pfp)
                }else if(picCode == "5"){
                    logoProfile.setBackgroundResource(R.drawable.love_pfp)
                }else if(picCode == "6"){
                    logoProfile.setBackgroundResource(R.drawable.moon_pfp)
                }else if(picCode == "7"){
                    logoProfile.setBackgroundResource(R.drawable.cat_pfp)
                }else if(picCode == "8"){
                    logoProfile.setBackgroundResource(R.drawable.axe_pfp)
                }else if(picCode == "9"){
                    logoProfile.setBackgroundResource(R.drawable.game_pfp)
                }

                if(tutorialCode == "False"){
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("tutorial", "True")
                    ShowIntro("Play Button", "Use This Button To Start Playing", playMenu, 2)
                }

                shopMenu.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_shopFragment)
                }

                settingMenu.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_upgradeFragment)
                }

                playMenu.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_categoryFragment)
                }

                rankMenu.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_rankFragment)
                }

                logoProfile.setOnClickListener {
                    navController.navigate(R.id.action_homeFragment_to_profileFragment)
                }
            }
    }

    private fun ShowIntro(title: String, text: String, viewId: View, type: Int) {
        GuideView.Builder(requireContext())
            .setTitle(title)
            .setContentText(text)
            .setTargetView(viewId)
            .setContentTextSize(12) //optional
            .setTitleTextSize(14) //optional
            .setDismissType(DismissType.anywhere) //optional - default dismissible by TargetView
            .setGuideListener {
                if (type == 2) {
                    ShowIntro(
                        "Shop Button",
                        "Use This Button To Enter The Shop",
                        shopMenu, 3
                    )
                } else if (type == 3) {
                    ShowIntro(
                        "Ranking Button",
                        "Use This Button To See The Leaderboard",
                        rankMenu, 4
                    )
                }else if (type == 4) {
                    ShowIntro(
                        "Upgrade Button",
                        "Use This Button To Upgrade Your Power",
                        settingMenu, 5
                    )
                }
            }
            .build()
            .show()
    }

}