package com.toeichero.project.Model

data class userAccount(
    var username: String,
    var email: String,
    var password: String,
    var activePhoto: Int,
    var photoInvent: List<Int>,
    var score: Int,
    var money: Int,
    var achievementList: List<Int>,
    var tutorial: String,
    var skinLevel: Int,
    var clotheLevel: Int,
    var tempScore: Int
)

data class userRankAccount(
    var username: String,
    var activePhoto: Int,
    var score: Int
)