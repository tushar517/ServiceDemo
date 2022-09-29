package com.example.servicedemo.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.servicedemo.MainActivity
import com.example.servicedemo.R

class ForegroundServices:Service() {

    private val CHANNEL_ID = "FOREGROUND_SERVICE"
    companion object{

        fun start(context: Context,message:String){
            val intent = Intent(context,ForegroundServices::class.java)
            intent.putExtra("msg",message)
            ContextCompat.startForegroundService(context,intent)
        }

        fun stop(context: Context){
            context.stopService(Intent(context,ForegroundServices::class.java))
        }
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val input = intent?.getStringExtra("msg")
        createNotificationChannel()
        val notificationIntent = Intent(this,MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            0
        )
        val notification = NotificationCompat.Builder(this,CHANNEL_ID)
            .setContentTitle("Foreground Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(1,notification)

        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID,"Foreground Service Channel",NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}