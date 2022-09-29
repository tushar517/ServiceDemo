package com.example.servicedemo.services

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.provider.Settings
import android.util.Log

class BoundService: Service() {
    lateinit var player: MediaPlayer
    override fun onBind(intent: Intent?): IBinder? {
        Log.i("MyTag","Service onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.i("MyTag","Service onStartCommand")

        player = MediaPlayer.create(this,Settings.System.DEFAULT_RINGTONE_URI)
        player.isLooping = true
        player.start()
        return super.onStartCommand(intent, flags, startId)

//        prevents service to start(System will not try to recreate the service after it is killed)
//        return START_NOT_STICKY

//        prevents service to start(System will try to recreate the service after it is killed)
//        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        Log.i("MyTag","Service onCreate")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        stopSelf()
        player.stop()
        Log.i("MyTag","Service onUnbind")
        return super.onUnbind(intent)
    }
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i("MyTag","Service onRebind")
    }

    override fun onDestroy() {
        super.onDestroy()
//        player.stop()
        Log.i("MyTag","Service onDestroy")
    }

    inner class MyBinder:Binder(){
        fun getService():BoundService{
            return this@BoundService
        }
    }
}