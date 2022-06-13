package com.toeichero.project.View.Home

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.toeichero.project.Model.shopItem
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.change_list.view.*

class ChangeAdapter(private var ownedList: List<shopItem>, private val changeActivity: Activity, private val firestore: FirebaseFirestore, private var activePhoto: Int): RecyclerView.Adapter<ChangeAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val image: ImageView = item.ownedShopImg
        val name: TextView = item.ownedItemName
        val rank: Button= item.useButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.change_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var temp = 0
        firestore.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                val picCode = it.getLong("activePhoto")!!.toInt()
                if(ownedList[position].id == picCode){
                    holder.rank.isEnabled = false
                    holder.rank.isClickable = false
                    holder.rank.text = "Used"
                    holder.rank.setBackgroundResource(R.drawable.btn_gray)
                }else{
                    holder.rank.isEnabled = true
                    holder.rank.isClickable = true
                    holder.rank.text = "Use"
                    holder.rank.setBackgroundResource(R.drawable.btn_use)
                }}

        holder.rank.setOnClickListener {
            val currId = ownedList[position].id
            firestore.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
                .update("activePhoto", currId)
            activePhoto = position + 1
            notifyDataSetChanged()
        }

        holder.name.text = ownedList[position].name
        if(ownedList[position].id == 1){
            holder.image.setImageResource(R.drawable.main_pfp)
        }
        if(ownedList[position].id == 2){
            holder.image.setImageResource(R.drawable.monster_pfp)
        }
        if(ownedList[position].id == 3){
            holder.image.setImageResource(R.drawable.robo_pfp)
        }
        if(ownedList[position].id == 4){
            holder.image.setImageResource(R.drawable.disc_pfp)
        }
        if(ownedList[position].id == 5){
            holder.image.setImageResource(R.drawable.love_pfp)
        }
        if(ownedList[position].id == 6){
            holder.image.setImageResource(R.drawable.moon_pfp)
        }
        if(ownedList[position].id == 7){
            holder.image.setImageResource(R.drawable.cat_pfp)
        }
        if(ownedList[position].id == 8){
            holder.image.setImageResource(R.drawable.axe_pfp)
        }
        if(ownedList[position].id == 9){
            holder.image.setImageResource(R.drawable.game_pfp)
        }
    }

    override fun getItemCount(): Int {
        return ownedList.size
    }

}