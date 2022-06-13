package com.toeichero.project.View.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toeichero.project.Model.badgeData
import com.toeichero.project.Model.userRankAccount
import com.toeichero.project.R
import com.toeichero.project.View.Login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private lateinit var database: FirebaseFirestore
    private lateinit var navController: NavController
    var findRank: ArrayList<userRankAccount> = ArrayList()
    var usernameActive: String = ""
    private lateinit var mAuth: FirebaseAuth
    var badgeListRecycler: ArrayList<badgeData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseFirestore.getInstance()
        navController = Navigation.findNavController(view)
        mAuth = FirebaseAuth.getInstance()

        profileBack.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_homeFragment)
        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navController.navigate(R.id.action_profileFragment_to_homeFragment)
                }
            })

        pfpProfile.setOnClickListener {
            navController.navigate(R.id.action_profileFragment_to_changeFragment)
        }

        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                usernameActive = it.getString("username").toString()
                val pfpId = it.getLong("activePhoto").toString()
                if(pfpId == "1"){
                   pfpProfile.setImageResource(R.drawable.main_pfp)
                }else if(pfpId == "2"){
                    pfpProfile.setImageResource(R.drawable.monster_pfp)
                }else if(pfpId == "3"){
                    pfpProfile.setImageResource(R.drawable.robo_pfp)
                }else if(pfpId == "4"){
                    pfpProfile.setImageResource(R.drawable.disc_pfp)
                }else if(pfpId == "5"){
                    pfpProfile.setImageResource(R.drawable.love_pfp)
                }else if(pfpId == "6"){
                    pfpProfile.setImageResource(R.drawable.moon_pfp)
                }else if(pfpId == "7"){
                    pfpProfile.setImageResource(R.drawable.cat_pfp)
                }else if(pfpId == "8"){
                    pfpProfile.setImageResource(R.drawable.axe_pfp)
                }else if(pfpId == "9"){
                    pfpProfile.setImageResource(R.drawable.game_pfp)
                }
                profileUsername.text = usernameActive
                val badgeList = it.get("achievementList")
                scoreTotal.text = it.getLong("score").toString()
                if(badgeList.toString().contains("1")){
                    badgeListRecycler.add(badgeData(1, R.drawable.regisachievement, "Create An Account"))
                }
                if(badgeList.toString().contains("2")){
                    badgeListRecycler.add(badgeData(2, R.drawable.skinachievement, "Reach Level 10 Skin"))
                }
                if(badgeList.toString().contains("3")){
                    badgeListRecycler.add(badgeData(3, R.drawable.love_pfp, "Get 1000 Scores"))
                }
                recyclerBadge.adapter = BadgeAdapter(badgeListRecycler, requireContext())
                database.collection("users")
                    .orderBy("score", Query.Direction.DESCENDING).get().addOnSuccessListener {
                        for(it in it){
                            findRank.add(userRankAccount(it.getString("username").toString(), it.getLong("activePhoto")!!.toInt(),it.getLong("score")!!.toInt()))
                        }
                        var indexRank: Int = 1
                        for(it in findRank){
                            if(it.username == usernameActive){
                                playerRank.text = "#" + indexRank
                            }else{
                                indexRank++
                            }
                        }
                    }
            }

        logoutBtn.setOnClickListener {
            mAuth.signOut()
            val intent = Intent (getActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            getActivity()?.startActivity(intent)
        }
    }

}