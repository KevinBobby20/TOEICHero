package com.toeichero.project.Model

data class shopItem(
    val id: Int,
    val image: Int,
    val name: String,
    val price: Int
)

data class MyShop(
    var ownedList: ArrayList<Int>
)