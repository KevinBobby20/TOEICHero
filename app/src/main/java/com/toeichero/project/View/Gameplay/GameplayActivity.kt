package com.toeichero.project.View.Gameplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toeichero.project.R
import com.toeichero.project.Service.BackgroundMusic

class GameplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameplay)

        val myService = Intent(this, BackgroundMusic::class.java)
        myService.setAction("PAUSE")
        startService(myService)
    }
}