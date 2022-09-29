package com.example.servicedemo

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import com.example.servicedemo.services.BackgroundService
import com.example.servicedemo.services.BoundService
import com.example.servicedemo.services.ForegroundServices

class MainActivity : AppCompatActivity() {
    var mBound:Boolean = false
    lateinit var mService:BoundService
    private val serviceConnection = object : ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            mBound = true
            val binder = service as BoundService.MyBinder
            mService = binder.getService()
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound = false
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        Bound Service runs till the component it is bounded to is alive
        */
        val startBoundBtn:Button = findViewById(R.id.startBoundBtn)
        val stopBoundBtn:Button = findViewById(R.id.stopBoundBtn)

        startBoundBtn.setOnClickListener {
            startRingtone()
        }

        stopBoundBtn.setOnClickListener {
            startActivity(Intent(this,SecondActivity::class.java))
        }

        /*
        Foreground Service runs even if the app is terminated
         */

        val startForegroundBtn:Button = findViewById(R.id.startForegroundBtn)
        val stopForegroundBtn:Button = findViewById(R.id.stopForegroundBtn)

        startForegroundBtn.setOnClickListener {
            ForegroundServices.start(this,"Foreground Service is running")
        }
        stopForegroundBtn.setOnClickListener {
            ForegroundServices.stop(this)
        }

        /*
        Background Service runs till the app is terminated
         */

//        startService(Intent(this,BackgroundService::class.java))

    }

    fun startRingtone(){
        val intent = Intent(this,BoundService::class.java)
        startService(intent)
    }

    override fun onDestroy() {
        Log.i("MyTag","Activity onDestroy")
//        stopService(Intent(this,BoundService::class.java))
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        Intent(this, BoundService::class.java).also { intent ->
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        Log.i("MyTag","Activity onStop")
        unbindService(serviceConnection)
        mBound = false
    }
}