package com.toeichero.project.View.Gameplay

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.toeichero.project.R
import com.toeichero.project.View.Home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.toeichero.project.Model.*
import kotlinx.android.synthetic.main.fragment_quiz.*

class QuizFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var database: FirebaseFirestore
    private lateinit var media: MediaPlayer
    var readingList: ArrayList<readingQuestion> = ArrayList()
    var readingList1: ArrayList<readingQuestion1> = ArrayList()
    var listenList: ArrayList<listenQuestion> = ArrayList()
    var listenList1: ArrayList<listenQuestion3> = ArrayList()
    var currNo = 0
    var convoList: ArrayList<convoQuestion> = ArrayList()
    private lateinit var timer: CountDownTimer
    var currScore = 0
    var rightAnswer = ""
    var multiplier = 1
    var updateScore = 0
    var clothesLevel = 0
    var money = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database = FirebaseFirestore.getInstance()
        navController = Navigation.findNavController(view)
        val status = requireActivity().intent.getIntExtra("Status", 0)
        val type = requireActivity().intent.getIntExtra("Type", 0)
        database.collection("users")
            .document(FirebaseAuth.getInstance().uid.toString())
            .get().addOnSuccessListener {
                multiplier = it.getLong("skinLevel")!!.toInt()
                updateScore = it.getLong("score")!!.toInt()
                clothesLevel = it.getLong("clotheLevel")!!.toInt()
                money = it.getLong("money")!!.toInt()
            }

        currPoint.text = currScore.toString()

        if(status == 1 && type == 3){
            readingTemplate.visibility = View.GONE
            ListenTemplate.visibility = View.VISIBLE
            noQuestionListen.text = currNo.toString() + " / 10"
            readingList1.add(readingQuestion1(R.drawable.readingonecom, "They have been cited for unsanitary conditions.", "They are In violation of the building code", "They have been cited for unsanitary conditions.", "They are undergoing renovations.", "They are open for only pan of the year."))
            readingList1.add(readingQuestion1(R.drawable.readingtwocom, "Peppo Mart", "Peppo Mart", "Valley Restaurant", "Mandy’s", "Lawville’s"))
            readingList1.add(readingQuestion1(R.drawable.readingthreecom, "Check its sinks and pipes", "Offer takeout service", "Change its menu ", "Check its sinks and pipes", "Renew its permit"))
            readingList1.add(readingQuestion1(R.drawable.readingfourcom, " One hundred executives of the top firms in the nation", "Jefferson's too one hundred managers", "Presidents from each of five hundred companies", "Forty-three leaders of the watch Industry", " One hundred executives of the top firms in the nation"))
            readingList1.add(readingQuestion1(R.drawable.readingfivecom, "When executives do their best work", "When executives do their best work", "How many hours most businesses are open", "What brands of watches top managers wear", "How close workers lived to their jobs"))
            readingList1.add(readingQuestion1(R.drawable.readingsixcom, "65", "75", "55", "44", "65"))
            readingList1.add(readingQuestion1(R.drawable.readingsevencom, "Fewer hotel rooms were rented than in the previous year", "The average room rate was \$35.30", "Fewer hotel rooms were rented than in the previous year", "Wages of hotel workers decreased by 5.5%", "More statistical reports were issued than in the previous year"))
            readingList1.add(readingQuestion1(R.drawable.readingeightcom, "Canada", "Canada", "Africa", "Central America", "Mexico"))
            readingList1.add(readingQuestion1(R.drawable.readingninecom, "Policy on Withdrawals", "Policy on Withdrawals", "Availability of Registration Forms", "Spring Course Offerings", "Procedures for Depositing Checks"))
            readingList1.add(readingQuestion1(R.drawable.readingtencom, "\$10", "\$15", "\$1", "\$10", "Nothing"))
            readingList1.add(readingQuestion1(R.drawable.readingelevencom, "If there is any space left in the second course", "If there is any space left in the second course", "If you have paid the handling charge", " If the first course is canceled because of low enrollment", "If your original space can be fitted"))

            timer = object : CountDownTimer(100000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemainingListen.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                        ,"money", (clothesLevel*currScore)+money)
                    answerAListen.isClickable = false
                    answerBListen.isClickable = false
                    answerCListen.isClickable = false
                    answerDListen.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }
            noQuestionListen.text = currNo.toString()
            answerAListen.setOnClickListener {
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerAListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerAListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerBListen.setOnClickListener {
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPointListen.text = currScore.toString()
                    answerBListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerBListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerCListen.setOnClickListener {
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerCListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerCListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerDListen.setOnClickListener {
                if (rightAnswer == "D"){
                    currScore = (1*multiplier + currScore)
                    answerDListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerDListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerDListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerDListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestionRead1()

            noQuestionListen.text = currNo.toString() + " / 10"
        }

        if(status == 1 && type == 2){
            readingTemplate.visibility = View.GONE
            ListenTemplate.visibility = View.VISIBLE
            noQuestionListen.text = currNo.toString() + " / 10"
            readingList1.add(readingQuestion1(R.drawable.readone, "Unattended", "Unattended", "Unseen", "Unwatched", "Unobserve"))
            readingList1.add(readingQuestion1(R.drawable.readtwo, "Valuables", "Values", "Valuables", "Precious", "Importance"))
            readingList1.add(readingQuestion1(R.drawable.readthree, "Reimburse", "Return", "Refund", "Reimburse", "Paid"))
            readingList1.add(readingQuestion1(R.drawable.readfour, "Cooperation", "Unification", "Peer Pressure", "Love", "Cooperation"))
            readingList1.add(readingQuestion1(R.drawable.readfive, "Alert", "Notice", "Alert", "Notify", "Notice"))
            readingList1.add(readingQuestion1(R.drawable.readsix, "Recent", "Late", "Recently", "Recent", "Lately"))
            readingList1.add(readingQuestion1(R.drawable.readseven, "Reputation", "Reputation", "Repute", "Report", "Score"))
            readingList1.add(readingQuestion1(R.drawable.readeight, "Premises", "Store", "Ground", "Building", "Premises"))
            readingList1.add(readingQuestion1(R.drawable.readnine, "More Spacious", "Most Spacious", "More Spacious", "More Spacious Than", "The Most Spacious"))
            readingList1.add(readingQuestion1(R.drawable.readten, "Renting", "Renting", "Rent", "Rents", "To Rent"))
            readingList1.add(readingQuestion1(R.drawable.readeleven, "Quickly", "Quickly", "Quick", "Quicker", "Quickest"))

            timer = object : CountDownTimer(100000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemainingListen.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                            ,"money", (clothesLevel*currScore)+money)
                    answerAListen.isClickable = false
                    answerBListen.isClickable = false
                    answerCListen.isClickable = false
                    answerDListen.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }
            noQuestionListen.text = currNo.toString()
            answerAListen.setOnClickListener {
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerAListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerAListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerBListen.setOnClickListener {
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPointListen.text = currScore.toString()
                    answerBListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerBListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerCListen.setOnClickListener {
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerCListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerCListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerDListen.setOnClickListener {
                if (rightAnswer == "D"){
                    currScore = (1*multiplier + currScore)
                    answerDListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerDListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerDListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerDListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionRead1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestionRead1()

            noQuestionListen.text = currNo.toString() + " / 10"
        }

        if(status == 2 && type == 2){
            readingTemplate.visibility = View.GONE
            ListenTemplate.visibility = View.VISIBLE
            answerDListen.visibility = View.INVISIBLE
            answerDListen.isClickable = false
            noQuestionListen.text = currNo.toString() + " / 10"
            listenList1.add(listenQuestion3(R.raw.qrone, R.drawable.ic_baseline_campaign_24, "A", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrtwo, R.drawable.ic_baseline_campaign_24, "B", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrthree, R.drawable.ic_baseline_campaign_24, "B", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrfour, R.drawable.ic_baseline_campaign_24, "C", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrfive, R.drawable.ic_baseline_campaign_24, "B", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrsix, R.drawable.ic_baseline_campaign_24, "A", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrseven, R.drawable.ic_baseline_campaign_24, "B", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qreight, R.drawable.ic_baseline_campaign_24, "C", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrnine, R.drawable.ic_baseline_campaign_24, "A", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrten, R.drawable.ic_baseline_campaign_24, "B", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qreleven, R.drawable.ic_baseline_campaign_24, "A", "A", "B", "C"))
            listenList1.add(listenQuestion3(R.raw.qrtwelve, R.drawable.ic_baseline_campaign_24, "B", "A", "B", "C"))

            timer = object : CountDownTimer(200000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemainingListen.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    media.stop()
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                            ,"money", (clothesLevel*currScore)+money)
                    answerAListen.isClickable = false
                    answerBListen.isClickable = false
                    answerCListen.isClickable = false
                    answerDListen.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }
            noQuestionListen.text = currNo.toString()

            answerAListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerAListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionListen1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerAListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerBListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPointListen.text = currScore.toString()
                    answerBListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerBListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerCListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerCListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionListen1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerCListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen1()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestionListen1()

            noQuestionListen.text = currNo.toString() + " / 10"
        }

        if(status == 2 && type == 1){
            readingTemplate.visibility = View.GONE
            ListenTemplate.visibility = View.VISIBLE
            noQuestionListen.text = currNo.toString() + " / 10"
            listenList.add(listenQuestion(R.raw.one, R.drawable.one, "A", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.two, R.drawable.two, "B", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.three, R.drawable.three, "D", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.four, R.drawable.four, "D", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.five, R.drawable.five, "D", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.six, R.drawable.six, "C", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.seven, R.drawable.seven, "C", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.eight, R.drawable.eight, "B", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.nine, R.drawable.nine, "C", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.ten, R.drawable.ten, "B", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.eleven, R.drawable.eleven, "A", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.twelve, R.drawable.twelve, "C", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.thirteen, R.drawable.thirthteen, "B", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.fourteen, R.drawable.fourteen, "A", "A", "B", "C", "D"))
            listenList.add(listenQuestion(R.raw.fifthteen, R.drawable.fifthteen, "A", "A", "B", "C", "D"))

            timer = object : CountDownTimer(150000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemainingListen.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    media.stop()
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                            ,"money", (clothesLevel*currScore)+money)
                    answerAListen.isClickable = false
                    answerBListen.isClickable = false
                    answerCListen.isClickable = false
                    answerDListen.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }
            noQuestionListen.text = currNo.toString()

            answerAListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerAListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerAListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerAListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerBListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPointListen.text = currScore.toString()
                    answerBListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerBListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerBListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerCListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerCListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerCListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerCListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            answerDListen.setOnClickListener {
                media.stop()
                if (rightAnswer == "D"){
                    currScore = (1*multiplier + currScore)
                    answerDListen.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerDListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPointListen.text = currScore.toString()
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }else {
                    answerDListen.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerDListen.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionListen()
                    noQuestionListen.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestionListen()

            noQuestionListen.text = currNo.toString() + " / 10"
        }

        if(status == 2 && type == 4){
            readingTemplate.visibility = View.VISIBLE
            ListenTemplate.visibility = View.GONE
            noQuestion.text = currNo.toString() + " / 10"
            convoList.add(
                convoQuestion(
                    "What is the main purpose of the advertisement?",
                    R.raw.talksonethree,
                    "To announce a public service",
                    "To sell a product",
                    "To announce a public service",
                    "To promote alcohol",
                    "To warn criminals"
                )
            )
            convoList.add(
                convoQuestion(
                    "Who is the intended audience?",
                    R.raw.talksonethree,
                    "Adults",
                    "Adults",
                    "Children",
                    "Women",
                    "Friends"
                )
            )
            convoList.add(
                convoQuestion(
                    "What is being offered?",
                    R.raw.talksonethree,
                    "Free cab rides for drunk drivers",
                    "A little bit of good cheer",
                    "Designated drivers",
                    "Tough winter driving",
                    "Free cab rides for drunk drivers"
                )
            )
            convoList.add(
                convoQuestion(
                    "Who is probably speaking?",
                    R.raw.talksfoursix,
                    "A football coach",
                    "A school teacher",
                    "A football coach",
                    "A company CEO",
                    "A movie star"
                )
            )
            convoList.add(
                convoQuestion(
                    "What is the main purpose of the speech?",
                    R.raw.talksfoursix,
                    "To motivate",
                    "To motivate",
                    "To inform",
                    "To entertain",
                    "To educate"
                )
            )
            convoList.add(
                convoQuestion(
                    "How does the speaker feel about his listeners?",
                    R.raw.talksfoursix,
                    "Confident",
                    "Wary",
                    "Skeptical",
                    "Confident",
                    "Satisfied"
                )
            )
            convoList.add(
                convoQuestion(
                    "What is the purpose of this report?",
                    R.raw.talkssevennine,
                    "To inform drivers of traffic conditions",
                    "To promote Tri-State Insurance Company",
                    "To announce a two-car injury accident",
                    "To clarify an error in a previous report",
                    "To inform drivers of traffic conditions"
                )
            )
            convoList.add(
                convoQuestion(
                    "Why is traffic moving slowly on part of State Route 95?",
                    R.raw.talkssevennine,
                    "Two cars collided at an exit.",
                    "Two cars collided at an exit.",
                    "There is road construction.",
                    "An accident was moved off the roadway.",
                    "There is a broken traffic signal."
                )
            )
            convoList.add(
                convoQuestion(
                    "How fast is traffic moving on the Interstate 1 bridge across the river?",
                    R.raw.talkssevennine,
                    "Extremely slowly",
                    "At a normal pace",
                    "Quicker than usual",
                    "Extremely slowly",
                    "Completely stopped"
                )
            )
            convoList.add(
                convoQuestion(
                    "Who is the speaker probably talking to?",
                    R.raw.talkstentwelve,
                    "Tourists",
                    "Farmers",
                    "Tourists",
                    "Managers",
                    "Toddlers"
                )
            )
            convoList.add(
                convoQuestion(
                    "What will happen first?",
                    R.raw.talkstentwelve,
                    "Watching a sheep dog",
                    "Picking apples",
                    "Visiting an opera house",
                    "Eating lunch",
                    "Watching a sheep dog"
                )
            )
            convoList.add(
                convoQuestion(
                    "Why will the listeners go to the opera house?",
                    R.raw.talkstentwelve,
                    "To view a renowned landmark",
                    "To view a renowned landmark",
                    "To have a special meal",
                    "To take music lessons",
                    "To meet a famous singer"
                )
            )
            timer = object : CountDownTimer(100000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemaining.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    media.stop()
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                            ,"money", (clothesLevel*currScore)+money)
                    answerA.isClickable = false
                    answerB.isClickable = false
                    answerC.isClickable = false
                    answerD.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }

            noQuestion.text = currNo.toString()

            answerA.setOnClickListener {
                media.stop()
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerA.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerA.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerA.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerA.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerB.setOnClickListener {
                media.stop()
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPoint.text = currScore.toString()
                    answerB.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerB.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerB.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerB.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerC.setOnClickListener {
                media.stop()
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerC.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerC.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerC.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerC.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerD.setOnClickListener {
                media.stop()
                if (rightAnswer == "D"){
                    currScore = (1*multiplier + currScore)
                    answerD.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerD.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerD.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerD.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestionConvo()

            noQuestion.text = currNo.toString() + " / 10"
        }

        if(status == 2 && type == 3){
            readingTemplate.visibility = View.VISIBLE
            ListenTemplate.visibility = View.GONE
            noQuestion.text = currNo.toString() + " / 10"
            convoList.add(
                convoQuestion(
                    "Where is this conversation most probably taking place?",
                    R.raw.convoonethree,
                    "In an airport",
                    "In an office",
                    "At a bus stop",
                    "In an airport",
                    "At a train station"
                )
            )
            convoList.add(
                convoQuestion(
                    "What is the purpose of the woman's trip?",
                    R.raw.convoonethree,
                    "Business",
                    "Business",
                    "Pleasure",
                    "Personal",
                    "Camping"
                )
            )
            convoList.add(
                convoQuestion(
                    "What does the man plan to do?",
                    R.raw.convoonethree,
                    "Take a vacation",
                    "Take a vacation",
                    "Work for two weeks",
                    "Visit family",
                    "Go to Chicago"
                )
            )
            convoList.add(
                convoQuestion(
                    "Where is the conversation taking place?",
                    R.raw.convofoursix,
                    "In a store.",
                    "In a store.",
                    "In an office.",
                    "At a school.",
                    "At a bank."
                )
            )
            convoList.add(
                convoQuestion(
                    "What does the woman ask the man?",
                    R.raw.convofoursix,
                    "For price information.",
                    "To place an order.",
                    "For price information.",
                    "For change for \$100.",
                    "If she can pay by check."
                )
            )
            convoList.add(
                convoQuestion(
                    "What does the man say about bulk discounts?",
                    R.raw.convofoursix,
                    "They are available for three or more cases.",
                    "There are none available.",
                    "They are available for five or more cases.",
                    "They are available for three or more cases.",
                    "They are available only on the first case.\n"
                )
            )
            convoList.add(
                convoQuestion(
                    "Where is the woman going?",
                    R.raw.convosevennine,
                    "To an office building.",
                    "To the train station.",
                    "To the dentist office.",
                    "To the hair salon.",
                    "To an office building."
                )
            )
            convoList.add(
                convoQuestion(
                    "What should the woman do at Bay Street?",
                    R.raw.convosevennine,
                    "Turn left.",
                    "Exit the freeway.",
                    "Turn left.",
                    "Turn right.",
                    "Park the car."
                )
            )
            convoList.add(
                convoQuestion(
                    "What does the man say about the headquarters building?",
                    R.raw.convosevennine,
                    "It's between the hair salon and dentist office.",
                    "It's next to the train station.",
                    "It's between the hair salon and dentist office.",
                    "It's located on Fourth Avenue.",
                    "It's on the left-hand side of the road."
                )
            )
            convoList.add(
                convoQuestion(
                    "Who are the speakers talking about?",
                    R.raw.convotentwelve,
                    "A former colleague",
                    "One of their children",
                    "Their next-door neighbor",
                    "A former colleague",
                    "A business client"
                )
            )
            convoList.add(
                convoQuestion(
                    "What is Mary's new profession?",
                    R.raw.convotentwelve,
                    "Teacher",
                    "Accountant",
                    "Teacher",
                    "Lawyer",
                    "Driver"
                )
            )
            convoList.add(
                convoQuestion(
                    "What does the man say about Mary?",
                    R.raw.convotentwelve,
                    "She will succeed in her new career.",
                    "She will succeed in her new career.",
                    "She is very good with children.",
                    "She has always been a hard worker.",
                    "She did not make a good decision."
                )
            )
            timer = object : CountDownTimer(100000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemaining.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    media.stop()
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                            ,"money", (clothesLevel*currScore)+money)
                    answerA.isClickable = false
                    answerB.isClickable = false
                    answerC.isClickable = false
                    answerD.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }

            noQuestion.text = currNo.toString()

            answerA.setOnClickListener {
                media.stop()
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerA.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerA.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerA.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerA.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerB.setOnClickListener {
                media.stop()
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPoint.text = currScore.toString()
                    answerB.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerB.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerB.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerB.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerC.setOnClickListener {
                media.stop()
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerC.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerC.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerC.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerC.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerD.setOnClickListener {
                media.stop()
                if (rightAnswer == "D"){
                    currScore = (1*multiplier + currScore)
                    answerD.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerD.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerD.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerD.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestionConvo()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestionConvo()

            noQuestion.text = currNo.toString() + " / 10"
        }

        if (status == 1 && type == 1) {
            readingTemplate.visibility = View.VISIBLE
            ListenTemplate.visibility = View.GONE
            noQuestion.text = currNo.toString() + " / 10"
            readingList.add(
                readingQuestion(
                    "The cost of the bus from the airport to the hotel is ___ $12.",
                    "Approximately",
                    "Approximately",
                    "Relatively",
                    "Plausibly",
                    "Reliably"
                )
            )
            readingList.add(
                readingQuestion(
                    "I'm afraid I'm ___; I've been working in the office since 6am.",
                    "Exhausted",
                    "Extended",
                    "Exhausted",
                    "Incensed",
                    "Invigorated"
                )
            )
            readingList.add(
                readingQuestion(
                    "In order to be efficient, you must ___ on the task at hand.",
                    "Focus",
                    "Strive",
                    "Focus",
                    "Aim",
                    "Grasp"
                )
            )
            readingList.add(
                readingQuestion(
                    "___ he is thin, he is strong.",
                    "Though",
                    "Because",
                    "But",
                    "As",
                    "Though"
                )
            )
            readingList.add(
                readingQuestion(
                    "A castle is a place ___ a king or queen lives.",
                    "Where",
                    "What",
                    "Who",
                    "Where",
                    "Were"
                )
            )
            readingList.add(
                readingQuestion(
                    "An actress is a woman ___ plays in films or theatre plays.",
                    "Who",
                    "What",
                    "Whose",
                    "That",
                    "Who"
                )
            )
            readingList.add(
                readingQuestion(
                    "Andy was worried ___ what to wear to the job interview.",
                    "About",
                    "For",
                    "For",
                    "About",
                    "At"
                )
            )
            readingList.add(
                readingQuestion(
                    "The flowers ___ grow in the garden are beautiful.",
                    "Which",
                    "Which",
                    "That",
                    "When",
                    "Where"
                )
            )
            readingList.add(
                readingQuestion(
                    "___ the last four years, Bay City's population has grown by about 10%.",
                    "During",
                    "As",
                    "Against",
                    "During",
                    "Below"
                )
            )
            readingList.add(
                readingQuestion(
                    "___ I don't get back tonight, my mother will certainly be upset.",
                    "If",
                    "Unless",
                    "If",
                    "Although",
                    "So"
                )
            )
            readingList.add(
                readingQuestion(
                    "Since 2009 they ___ their son every year. (to visit)",
                    "Have visited",
                    "Visits",
                    "Have been visited",
                    "Have visited",
                    "Visit"
                )
            )
            readingList.add(
                readingQuestion(
                    "I (be) ___ a student in UMN in 2016.",
                    "Was",
                    "Were",
                    "To be",
                    "To Was",
                    "Was"
                )
            )
            readingList.add(
                readingQuestion(
                    "I ___ to inform you that your application for the job has not been successful.",
                    "Regret",
                    "Regret",
                    "Scorn",
                    "Afraid",
                    "Hesitate"
                )
            )
            readingList.add(
                readingQuestion(
                    "Questions should be ___ forwarded to the director.",
                    "Directly",
                    "Freshly",
                    "Directly",
                    "Easily",
                    "Uniquely"
                )
            )
            readingList.add(
                readingQuestion(
                    "Ms. Siti will be attending the conference with ___ developer team.",
                    "Her",
                    "His",
                    "Her",
                    "She",
                    "He"
                )
            )
            readingList.add(
                readingQuestion(
                    "Do you think the project ___ by monday?",
                    "Will be finished",
                    "Finish",
                    "Will be finished",
                    "Will Finish",
                    "Finishes"
                )
            )
            readingList.add(
                readingQuestion(
                    "Mr. Budi was late for the meeting because the flight was ___.",
                    "Delayed",
                    "Lost",
                    "Dismissed",
                    "Delayed",
                    "Lengthened"
                )
            )
            readingList.add(
                readingQuestion(
                    "If you were injured in the workplace, you should be eligible for ___",
                    "Compensation",
                    "Compensation",
                    "Demotion",
                    "Promotion",
                    "Rejection"
                )
            )
            readingList.add(
                readingQuestion(
                    "Should there be any requests for schedule changes, please notify us ___",
                    "Promptly",
                    "Prompt",
                    "Prompting",
                    "Prompted",
                    "Promptly"
                )
            )
            readingList.add(
                readingQuestion(
                    "This is the man ___ built our garden.",
                    "Who",
                    "Who",
                    "Whose",
                    "That",
                    "Those"
                )
            )
            timer = object : CountDownTimer(100000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    timeRemaining.text = (millisUntilFinished / 1000).toString()
                }

                override fun onFinish() {
                    database.collection("users")
                        .document(FirebaseAuth.getInstance().uid.toString())
                        .update("score", currScore+updateScore
                            , "tempScore", currScore
                            ,"money", (clothesLevel*currScore)+money)
                    answerA.isClickable = false
                    answerB.isClickable = false
                    answerC.isClickable = false
                    answerD.isClickable = false
                    Handler().postDelayed({
                        navController.navigate(R.id.action_quizFragment_to_endGameFragment)
                    }, 600)
                }
            }

            noQuestion.text = currNo.toString()

            answerA.setOnClickListener {
                if (rightAnswer == "A"){
                    currScore = (1*multiplier + currScore)
                    answerA.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerA.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerA.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerA.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerB.setOnClickListener {
                if (rightAnswer == "B"){
                    currScore = (1*multiplier + currScore)
                    currPoint.text = currScore.toString()
                    answerB.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerB.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerB.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerB.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerC.setOnClickListener {
                if (rightAnswer == "C"){
                    currScore = (1*multiplier + currScore)
                    answerC.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerC.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerC.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerC.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            answerD.setOnClickListener {
                if (rightAnswer == "D"){
                    currScore = (1*multiplier + currScore)
                    answerD.setBackgroundResource(R.drawable.btn_green_dialog)
                    Handler().postDelayed({
                        answerD.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    currPoint.text = currScore.toString()
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }else {
                    answerD.setBackgroundResource(R.drawable.btn_orange)
                    Handler().postDelayed({
                        answerD.setBackgroundResource(R.drawable.btn_white)
                    }, 500)
                    randomQuestion()
                    noQuestion.text = currNo.toString() + " / 10"
                }
            }

            timer.start()

            randomQuestion()

            noQuestion.text = currNo.toString() + " / 10"

        }

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if(status == 2){
                        media.stop()
                    }
                    timer.cancel()
                    Handler().postDelayed({
                        val intent = Intent (getActivity(), HomeActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        getActivity()?.startActivity(intent)
                    }, 600)
                }
            })
    }

    fun randomQuestionListen1(){
        val number = listenList1.size - 1
        val values = (0..number).random()
        media = MediaPlayer.create(requireContext(), listenList1[values].question)
        media.start()
        questionActiveListen.setImageResource(listenList1[values].image)
        answerAListenText.text = listenList1[values].aOption
        answerBListenText.text = listenList1[values].bOption
        answerCListenText.text = listenList1[values].cOption
        if(listenList1[values].rightAnswer == listenList1[values].aOption){
            rightAnswer = "A"
        }else if(listenList1[values].rightAnswer == listenList1[values].bOption){
            rightAnswer = "B"
        }else if(listenList1[values].rightAnswer == listenList1[values].cOption){
            rightAnswer = "C"
        }
        currNo++
        listenList1.removeAt(values)
        if(currNo > 10){
            media.stop()
            database.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
                .update("score", currScore+updateScore
                    , "tempScore", currScore
                    ,"money", (clothesLevel*currScore)+money)
            answerAListen.isClickable = false
            answerBListen.isClickable = false
            answerCListen.isClickable = false
            timer.cancel()
            Handler().postDelayed({
                navController.navigate(R.id.action_quizFragment_to_endGameFragment)
            }, 600)
        }
    }

    fun randomQuestionListen(){
        val number = listenList.size - 1
        val values = (0..number).random()
        media = MediaPlayer.create(requireContext(), listenList[values].question)
        media.start()
        questionActiveListen.setImageResource(listenList[values].image)
        answerAListenText.text = listenList[values].aOption
        answerBListenText.text = listenList[values].bOption
        answerCListenText.text = listenList[values].cOption
        answerDListenText.text = listenList[values].dOption
        if(listenList[values].rightAnswer == listenList[values].aOption){
            rightAnswer = "A"
        }else if(listenList[values].rightAnswer == listenList[values].bOption){
            rightAnswer = "B"
        }else if(listenList[values].rightAnswer == listenList[values].cOption){
            rightAnswer = "C"
        }else if(listenList[values].rightAnswer == listenList[values].dOption){
            rightAnswer = "D"
        }
        currNo++
        listenList.removeAt(values)
        if(currNo > 10){
            media.stop()
            database.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
                .update("score", currScore+updateScore
                    , "tempScore", currScore
                    ,"money", (clothesLevel*currScore)+money)
            answerAListen.isClickable = false
            answerBListen.isClickable = false
            answerCListen.isClickable = false
            answerDListen.isClickable = false
            timer.cancel()
            Handler().postDelayed({
                navController.navigate(R.id.action_quizFragment_to_endGameFragment)
            }, 600)
        }
    }


    fun randomQuestion() {
        val number = readingList.size - 1
        val values = (0..number).random()
        questionActive.text = readingList[values].question
        answerAText.text = readingList[values].aOption
        answerBText.text = readingList[values].bOption
        answerCText.text = readingList[values].cOption
        answerDText.text = readingList[values].dOption
        if(readingList[values].rightAnswer == readingList[values].aOption){
            rightAnswer = "A"
        }else if(readingList[values].rightAnswer == readingList[values].bOption){
            rightAnswer = "B"
        }else if(readingList[values].rightAnswer == readingList[values].cOption){
            rightAnswer = "C"
        }else if(readingList[values].rightAnswer == readingList[values].dOption){
            rightAnswer = "D"
        }
        currNo++
        readingList.removeAt(values)
        if(currNo > 10){
           database.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
               .update("score", currScore+updateScore
                   , "tempScore", currScore
                   ,"money", (clothesLevel*currScore)+money)
            answerA.isClickable = false
            answerB.isClickable = false
            answerC.isClickable = false
            answerD.isClickable = false
            timer.cancel()
            Handler().postDelayed({
                navController.navigate(R.id.action_quizFragment_to_endGameFragment)
            }, 600)
        }
    }

    fun randomQuestionConvo(){
        val number = convoList.size - 1
        val values = (0..number).random()
        media = MediaPlayer.create(requireContext(), convoList[values].sound)
        media.start()
        questionActive.text = convoList[values].question
        answerAText.text = convoList[values].aOption
        answerBText.text = convoList[values].bOption
        answerCText.text = convoList[values].cOption
        answerDText.text = convoList[values].dOption
        if(convoList[values].rightAnswer == convoList[values].aOption){
            rightAnswer = "A"
        }else if(convoList[values].rightAnswer == convoList[values].bOption){
            rightAnswer = "B"
        }else if(convoList[values].rightAnswer == convoList[values].cOption){
            rightAnswer = "C"
        }else if(convoList[values].rightAnswer == convoList[values].dOption){
            rightAnswer = "D"
        }
        currNo++
        convoList.removeAt(values)
        if(currNo > 10){
            media.stop()
            database.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
                .update("score", currScore+updateScore
                    , "tempScore", currScore
                    ,"money", (clothesLevel*currScore)+money)
            answerA.isClickable = false
            answerB.isClickable = false
            answerC.isClickable = false
            answerD.isClickable = false
            timer.cancel()
            Handler().postDelayed({
                navController.navigate(R.id.action_quizFragment_to_endGameFragment)
            }, 600)
        }
    }

    fun randomQuestionRead1(){
        val number = readingList1.size - 1
        val values = (0..number).random()
        questionActiveListen.setImageResource(readingList1[values].question)
        answerAListenText.text = readingList1[values].aOption
        answerBListenText.text = readingList1[values].bOption
        answerCListenText.text = readingList1[values].cOption
        answerDListenText.text = readingList1[values].dOption
        if(readingList1[values].rightAnswer == readingList1[values].aOption){
            rightAnswer = "A"
        }else if(readingList1[values].rightAnswer == readingList1[values].bOption){
            rightAnswer = "B"
        }else if(readingList1[values].rightAnswer == readingList1[values].cOption){
            rightAnswer = "C"
        }else if(readingList1[values].rightAnswer == readingList1[values].dOption){
            rightAnswer = "D"
        }
        currNo++
        readingList1.removeAt(values)
        if(currNo > 10){
            database.collection("users")
                .document(FirebaseAuth.getInstance().uid.toString())
                .update("score", currScore+updateScore
                    , "tempScore", currScore
                    ,"money", (clothesLevel*currScore)+money)
            answerAListen.isClickable = false
            answerBListen.isClickable = false
            answerCListen.isClickable = false
            answerDListen.isClickable = false
            timer.cancel()
            Handler().postDelayed({
                navController.navigate(R.id.action_quizFragment_to_endGameFragment)
            }, 600)
        }
    }
}