package com.toeichero.project.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.toeichero.project.Model.userRankAccount
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_rank.*

class RankFragment : Fragment() {
    private lateinit var database: FirebaseFirestore
    var list: ArrayList<userRankAccount> = ArrayList()
    private lateinit var navController: NavController
    var namaActive: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        rankBack.setOnClickListener {
            navController.navigateUp()
        }

        database = FirebaseFirestore.getInstance()

        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                namaActive = it.getString("username").toString()
                database.collection("users")
                    .orderBy("score", Query.Direction.DESCENDING).get().addOnSuccessListener {
                        for(it in it){
                            list.add(userRankAccount(it.getString("username").toString(), it.getLong("activePhoto")!!.toInt(),it.getLong("score")!!.toInt()))
                        }
                        rankList.adapter = RankAdapter(list, namaActive)
                        rankList.layoutManager = LinearLayoutManager(requireContext())
                        rankList.setHasFixedSize(true)
                    }
            }
    }
}