package com.example.servicedemo.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class BackgroundService:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        Log.i("MyTag","Background Service onBind")

        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("MyTag","Background Service onStartCommand")
        Thread {
            run {
                while (true) {
                    Log.i("MyBackgroundService", "Background Service is running....")
                    try {
                        Thread.sleep(3000)
                    } catch (ex: Exception) {
                        Log.i("MyServiceException", ex.message.toString())
                    }
                }
            }
        }.start()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        Log.i("MyTag","Background Service onUnbind")
    }
    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.i("MyTag","Background Service onRebind")
    }

    override fun onDestroy() {
        super.onDestroy()
//        player.stop()
        Log.i("MyTag","Background Service onDestroy")
    }

}