package com.toeichero.project.View.Onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.toeichero.project.R
import com.toeichero.project.Service.BackgroundMusic
import com.toeichero.project.View.Home.HomeActivity
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    lateinit var handler: Handler
    lateinit var mAuth: FirebaseAuth

    override fun onPause() {
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mAuth = FirebaseAuth.getInstance()

        handler = Handler()
        handler.postDelayed({
            if(mAuth.currentUser != null){
                val svc = Intent(this, BackgroundMusic::class.java)
                svc.setAction("PLAY")
                startService(svc)
                val intent = Intent (this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }else{
                val svc = Intent(this, BackgroundMusic::class.java)
                svc.setAction("PLAY")
                startService(svc)
                val intent = Intent(this, CarouselActivity::class.java)
                startActivity(intent)
                finish()
            }
        },3000)

    }
}