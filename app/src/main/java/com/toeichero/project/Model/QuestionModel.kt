package com.toeichero.project.Model

data class readingQuestion(
    val question: String,
    val rightAnswer: String,
    val aOption: String,
    val bOption: String,
    val cOption: String,
    val dOption: String
)

data class convoQuestion(
    val question: String,
    val sound: Int,
    val rightAnswer: String,
    val aOption: String,
    val bOption: String,
    val cOption: String,
    val dOption: String
)

data class readingQuestion1(
    val question: Int,
    val rightAnswer: String,
    val aOption: String,
    val bOption: String,
    val cOption: String,
    val dOption: String
)

data class listenQuestion(
    val question: Int,
    val image: Int,
    val rightAnswer: String,
    val aOption: String,
    val bOption: String,
    val cOption: String,
    val dOption: String
)

data class listenQuestion3(
    val question: Int,
    val image: Int,
    val rightAnswer: String,
    val aOption: String,
    val bOption: String,
    val cOption: String
)

