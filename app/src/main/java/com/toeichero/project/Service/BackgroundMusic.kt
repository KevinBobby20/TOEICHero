package com.toeichero.project.Service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.toeichero.project.R

import android.media.MediaPlayer


class BackgroundMusic: Service() {
    var player: MediaPlayer? = null
    override fun onBind(arg0: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.ukulele)
        player!!.isLooping = true // Set looping
        player!!.setVolume(100f, 100f)
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (intent.getAction().equals("PLAY")) {
            player!!.start()
        }

        if (intent.getAction().equals("PAUSE")) {
            player!!.pause()
        }
        return START_STICKY
    }

    override fun onStart(intent: Intent, startId: Int) {
        // TO DO
    }

    override fun onDestroy() {
        player!!.stop()
        player!!.release()
    }

    override fun onLowMemory() {}

    companion object {
        private val TAG: String? = null
    }
}