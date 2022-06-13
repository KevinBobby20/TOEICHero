package com.toeichero.project.View.Home

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.toeichero.project.Model.shopItem
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.shop_dialog.view.*
import kotlinx.android.synthetic.main.shop_list.view.*


class ShopAdapter(private var shopList: List<shopItem>, private val shopActivity: Activity, private val firestore: FirebaseFirestore, private val priceText: TextView): RecyclerView.Adapter<ShopAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        val image: ImageView = item.shopImg
        val name: TextView = item.itemName
        val price: TextView = item.textMoneyList
        val card: CardView = item.mainCardShop
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.shop_list, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = shopList[position]

        holder.image.setImageResource(currentItem.image)
        holder.name.text = currentItem.name
        holder.price.text = currentItem.price.toString()

        holder.card.setOnClickListener {
            Log.e("Test", currentItem.id.toString())
            shopDialog(currentItem.id, currentItem.price, position)
        }
    }

    override fun getItemCount(): Int {
        return shopList.size
    }

    private fun shopDialog(id: Int, price: Int, position: Int) {
        var newPrice: Int = 0
        var ownedMoney: Int = 0
        var list1: ArrayList<Int> = ArrayList()
        var list: ArrayList<shopItem> = ArrayList()
        val mDialogView = LayoutInflater.from(shopActivity).inflate(R.layout.shop_dialog, null)
        val mBuilder = AlertDialog.Builder(shopActivity)
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                shopActivity,
                R.drawable.dialog_background
            )
        )

        firestore.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
                .get().addOnSuccessListener {
                    val myPfpList = it.get("photoInvent")
                    newPrice = it.getLong("money")?.minus(price)!!.toInt()
                ownedMoney = it.getLong("money")!!.toInt()
                Log.e("List", myPfpList.toString())
                list1.add(1)
                if(myPfpList.toString().contains("2")){
                    list1.add(2)
                }
                if(myPfpList.toString().contains("3")){
                    list1.add(3)
                }
                if(myPfpList.toString().contains("4")){
                    list1.add(4)
                }
                if(myPfpList.toString().contains("5")){
                    list1.add(5)
                }
                if(myPfpList.toString().contains("6")){
                    list1.add(6)
                }
                if(myPfpList.toString().contains("7")){
                    list1.add(7)
                }
                if(myPfpList.toString().contains("8")){
                    list1.add(8)
                }
                if(myPfpList.toString().contains("9")){
                    list1.add(9)
                }
                list1.add(id)
            }

        mDialogView.dialog_yes.setOnClickListener {

            if (price > ownedMoney){
                Toast.makeText(shopActivity,"Not Enough Money", Toast.LENGTH_SHORT).show();
            }else{
                firestore.collection("users")
                    .document(FirebaseAuth.getInstance().uid.toString())
                    .update("photoInvent", list1,
                        "money", newPrice)

                firestore.collection("users")
                    .document(FirebaseAuth.getInstance().uid.toString())
                    .get().addOnSuccessListener {
                        val myPfpList = it.get("photoInvent")
                        Log.e("List", myPfpList.toString())
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
                        shopList = list
                        priceText.text = it.getLong("money").toString()
                        notifyDataSetChanged()
                    }
            }
            mAlertDialog.dismiss()
        }

        mDialogView.dialog_no.setOnClickListener {
            mAlertDialog.dismiss()
        }
    }

}