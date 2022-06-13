package com.toeichero.project.View.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toeichero.project.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_upgrade.*
import kotlinx.android.synthetic.main.upgrade_dialog.view.*

class UpgradeFragment : Fragment() {

    private lateinit var database: FirebaseFirestore
    private lateinit var navController: NavController
    var list: ArrayList<Int> = ArrayList()
    var skinLevel: Int = 0
    var clothesLevel: Int = 0
    var currMoney: Int = 0
    var dataAchieve = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upgrade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        database = FirebaseFirestore.getInstance()
        navController = Navigation.findNavController(view)
        upgradeBack.setOnClickListener {
            navController.navigateUp()
        }
        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                skinLevel = it.getLong("skinLevel")!!.toInt()
                clothesLevel = it.getLong("clotheLevel")!!.toInt()
                currMoney = it.getLong("money")!!.toInt()
                dataAchieve = it.get("achievementList").toString()
                if(dataAchieve.contains("1")){
                    list.add(1)
                }
                if(dataAchieve.contains("3")){
                    list.add(3)
                }
                upgradeMoney.text = currMoney.toString()
                if(skinLevel == 1){
                    skinExp.text = "Skin: Water (1x Score)"
                    skin.setImageResource(R.drawable.first_skin)
                }else if(skinLevel == 2){
                    skinExp.text = "Skin: Blood (2x Score)"
                    skin.setImageResource(R.drawable.skin_level_two)
                }else if(skinLevel == 3){
                    skinExp.text = "Skin: Pink (3x Score)"
                    skin.setImageResource(R.drawable.skin_level_three)
                }else if(skinLevel == 4){
                    skinExp.text = "Skin: Poison (4x Score)"
                    skin.setImageResource(R.drawable.skin_level_four)
                }else if(skinLevel == 5){
                    skinExp.text = "Skin: Milk (5x Score)"
                    skin.setImageResource(R.drawable.five_upgrade)
                }else if(skinLevel == 6){
                    skinExp.text = "Skin: Coffee (6x Score)"
                    skin.setImageResource(R.drawable.skin_level_six)
                }else if(skinLevel == 7){
                    skinExp.text = "Skin: Lemon (7x Score)"
                    skin.setImageResource(R.drawable.skin_level_seven)
                }else if(skinLevel == 8){
                    skinExp.text = "Skin: Melon (8x Score)"
                    skin.setImageResource(R.drawable.skin_level_eight)
                }else if(skinLevel == 9){
                    skinExp.text = "Skin: Orange (9x Score)"
                    skin.setImageResource(R.drawable.skin_level_nine)
                }else if(skinLevel == 10){
                    skinExp.text = "Skin: Matcha (10x Score)"
                    skin.setImageResource(R.drawable.skin_level_ten)
                }

                if(clothesLevel == 1){
                    clothesExp.text = "Clothes: Jersey (1x Coin)"
                    clothes.setImageResource(R.drawable.first_clothes)
                }else if(clothesLevel == 2){
                    clothesExp.text = "Clothes: Overall (2x Coin)"
                    clothes.setImageResource(R.drawable.level_2_overall)
                }else if(clothesLevel == 3){
                    clothesExp.text = "Clothes: Cape (3x Coin)"
                    clothes.setImageResource(R.drawable.level_3_cape)
                }else if(clothesLevel== 4){
                    clothesExp.text = "Clothes: Hoodie (4x Coin)"
                    clothes.setImageResource(R.drawable.level_4_hoodie)
                }else if(clothesLevel == 5){
                    clothesExp.text = "Clothes: Shirt n Tie (5x Coin)"
                    clothes.setImageResource(R.drawable.level_5_shirtntie)
                }else if(clothesLevel == 6){
                    clothesExp.text = "Clothes: Paramedic (6x Coin)"
                    clothes.setImageResource(R.drawable.level_6_paramedic)
                }else if(clothesLevel == 7){
                    clothesExp.text = "Clothes: White Suit (7x Coin)"
                    clothes.setImageResource(R.drawable.level_7_suit)
                }else if(clothesLevel == 8){
                    clothesExp.text = "Clothes: Black Suit (8x Coin)"
                    clothes.setImageResource(R.drawable.level_8_blacksuit)
                }else if(clothesLevel == 9){
                    clothesExp.text = "Clothes: Naruto (9x Coin)"
                    clothes.setImageResource(R.drawable.level_9_clothes)
                }else if(clothesLevel == 10){
                    clothesExp.text = "Clothes: Goku (10x Coin)"
                    clothes.setImageResource(R.drawable.level_10_clothes)
                }
            }
        skinButton.setOnClickListener {
            if(skinLevel < 10){
                database.collection("users")
                    .document(FirebaseAuth.getInstance().uid.toString())
                    .get().addOnSuccessListener {
                        var tempMoney = it.getLong("money")!!.toInt()
                        upgradeDialog("Skin", (100 * skinLevel), skinLevel, tempMoney)}
            }else{
                Toast.makeText(requireActivity(),"You Already Reach Maximal Level", Toast.LENGTH_SHORT).show()
            }
        }

        clothesButton.setOnClickListener {
            if(clothesLevel < 10){
                database.collection("users")
                    .document(FirebaseAuth.getInstance().uid.toString())
                    .get().addOnSuccessListener {
                        var tempMoney = it.getLong("money")!!.toInt()
                        upgradeDialog("Clothes", (100 * clothesLevel), clothesLevel, tempMoney)}
            }else{
                Toast.makeText(requireActivity(),"You Already Reach Maximal Level", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun upgradeDialog(type: String, price: Int, currLevel: Int, currMoney: Int) {
        val mDialogView = LayoutInflater.from(requireActivity()).inflate(R.layout.upgrade_dialog, null)
        val mBuilder = AlertDialog.Builder(requireActivity())
            .setView(mDialogView)
        val mAlertDialog = mBuilder.show()
        mAlertDialog.window?.setBackgroundDrawable(
            AppCompatResources.getDrawable(
                requireActivity(),
                R.drawable.dialog_background
            )
        )
        mDialogView.dialog_no_upgrade.setOnClickListener {
            mAlertDialog.dismiss()
        }
        mDialogView.dialog_upgrade_text.text = "Are You Sure You Want To Upgrade This For " + price + " Coin?"
        mDialogView.dialog_yes_upgrade.setOnClickListener {
            if(type == "Skin"){
                if(currMoney >= price){
                    var priceNow =  currMoney - price
                    if((currLevel + 1) == 10){
                        if(!dataAchieve.contains("2")){
                            list.add(2)
                            Toast.makeText(requireActivity(),"You Get New Achievement !", Toast.LENGTH_SHORT).show()
                        }
                    }
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("skinLevel", currLevel + 1,
                            "money", priceNow,
                        "achievementList", list)
                    Toast.makeText(requireActivity(),"Upgrade Success", Toast.LENGTH_SHORT).show()
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .get().addOnSuccessListener {
                            skinLevel = it.getLong("skinLevel")!!.toInt()
                            clothesLevel = it.getLong("clotheLevel")!!.toInt()
                            var currMoney = it.getLong("money")!!.toInt()
                            upgradeMoney.text = currMoney.toString()
                            if(skinLevel == 1){
                                skinExp.text = "Skin: Water (1x Score)"
                                skin.setImageResource(R.drawable.first_skin)
                            }else if(skinLevel == 2){
                                skinExp.text = "Skin: Blood (2x Score)"
                                skin.setImageResource(R.drawable.skin_level_two)
                            }else if(skinLevel == 3){
                                skinExp.text = "Skin: Pink (3x Score)"
                                skin.setImageResource(R.drawable.skin_level_three)
                            }else if(skinLevel == 4){
                                skinExp.text = "Skin: Poison (4x Score)"
                                skin.setImageResource(R.drawable.skin_level_four)
                            }else if(skinLevel == 5){
                                skinExp.text = "Skin: Milk (5x Score)"
                                skin.setImageResource(R.drawable.five_upgrade)
                            }else if(skinLevel == 6){
                                skinExp.text = "Skin: Coffee (6x Score)"
                                skin.setImageResource(R.drawable.skin_level_six)
                            }else if(skinLevel == 7){
                                skinExp.text = "Skin: Lemon (7x Score)"
                                skin.setImageResource(R.drawable.skin_level_seven)
                            }else if(skinLevel == 8){
                                skinExp.text = "Skin: Melon (8x Score)"
                                skin.setImageResource(R.drawable.skin_level_eight)
                            }else if(skinLevel == 9){
                                skinExp.text = "Skin: Orange (9x Score)"
                                skin.setImageResource(R.drawable.skin_level_nine)
                            }else if(skinLevel == 10){
                                skinExp.text = "Skin: Matcha (10x Score)"
                                skin.setImageResource(R.drawable.skin_level_ten)
                            }else{
                                Toast.makeText(requireActivity(),"You Already Reach Maximal Level", Toast.LENGTH_SHORT).show()
                            }
                        }
                    mAlertDialog.dismiss()
                }else{
                    Toast.makeText(requireActivity(),"Not Enough Coin", Toast.LENGTH_SHORT).show()
                }
            }else if(type == "Clothes"){
                if(currMoney >= price){
                    var priceNow =  currMoney - price
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("clotheLevel", currLevel + 1,
                            "money", priceNow,
                        "achievementList", list)
                    Toast.makeText(requireActivity(),"Upgrade Success", Toast.LENGTH_SHORT).show()
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .get().addOnSuccessListener {
                            skinLevel = it.getLong("skinLevel")!!.toInt()
                            clothesLevel = it.getLong("clotheLevel")!!.toInt()
                            var currMoney = it.getLong("money")!!.toInt()
                            upgradeMoney.text = currMoney.toString()
                            if(clothesLevel == 1){
                                clothesExp.text = "Clothes: Jersey (1x Coin)"
                                clothes.setImageResource(R.drawable.first_clothes)
                            }else if(clothesLevel == 2){
                                clothesExp.text = "Clothes: Overall (2x Coin)"
                                clothes.setImageResource(R.drawable.level_2_overall)
                            }else if(clothesLevel == 3){
                                clothesExp.text = "Clothes: Cape (3x Coin)"
                                clothes.setImageResource(R.drawable.level_3_cape)
                            }else if(clothesLevel== 4){
                                clothesExp.text = "Clothes: Hoodie (4x Coin)"
                                clothes.setImageResource(R.drawable.level_4_hoodie)
                            }else if(clothesLevel == 5){
                                clothesExp.text = "Clothes: Shirt n Tie (5x Coin)"
                                clothes.setImageResource(R.drawable.level_5_shirtntie)
                            }else if(clothesLevel == 6){
                                clothesExp.text = "Clothes: Paramedic (6x Coin)"
                                clothes.setImageResource(R.drawable.level_6_paramedic)
                            }else if(clothesLevel == 7){
                                clothesExp.text = "Clothes: White Suit (7x Coin)"
                                clothes.setImageResource(R.drawable.level_7_suit)
                            }else if(clothesLevel == 8){
                                clothesExp.text = "Clothes: Black Suit (8x Coin)"
                                clothes.setImageResource(R.drawable.level_8_blacksuit)
                            }else if(clothesLevel == 9){
                                clothesExp.text = "Clothes: Naruto (9x Coin)"
                                clothes.setImageResource(R.drawable.level_9_clothes)
                            }else if(clothesLevel == 10){
                                clothesExp.text = "Clothes: Goku (10x Coin)"
                                clothes.setImageResource(R.drawable.level_10_clothes)
                            }else{
                                Toast.makeText(requireActivity(),"You Already Reach Maximal Level", Toast.LENGTH_SHORT).show()
                            }
                        }
                    mAlertDialog.dismiss()
                }else{
                    Toast.makeText(requireActivity(),"Not Enough Coin", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}