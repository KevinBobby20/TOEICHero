package com.toeichero.project.View.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toeichero.project.R
import com.toeichero.project.Service.BackgroundMusic

class RegisterActivity : AppCompatActivity() {

    override fun onPause() {
        super.onPause()
        val myService = Intent(this, BackgroundMusic::class.java)
        myService.setAction("PAUSE")
        startService(myService)
    }

    override fun onResume() {
        super.onResume()
        val myService = Intent(this, BackgroundMusic::class.java)
        myService.setAction("PLAY")
        startService(myService)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val myService = Intent(this, BackgroundMusic::class.java)
        myService.setAction("PLAY")
        startService(myService)
    }
}