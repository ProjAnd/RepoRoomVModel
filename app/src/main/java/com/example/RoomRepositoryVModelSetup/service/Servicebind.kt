package com.example.RoomRepositoryVModelSetup.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

open class Servicebind: Service() {
    private val TAG = "Servicebind"
    private val binder = MyBinder()
    private var count = 0


    // My Binder
    inner class MyBinder : Binder() {
        fun getServiceInstance():Servicebind{
            return Servicebind()
        }
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate is invoked")

      object: Thread(){
          override fun run() {
              super.run()
              while (count<6){
                  try {
                      Thread.sleep(1000)
                  }catch (ex: Exception){

                  }
                   count++
                    val intent = Intent()
                    intent.action = MY_ACTION
                    intent.putExtra("COUNT", count)
                    sendBroadcast(intent)

              }
          }
      }.start()

    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind is invoked")
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind is invoked")
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        count = 0
        Log.d(TAG, "onDestroy is invoked")
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d(TAG, "onRebind is invoked")
    }

    companion object{
        val MY_ACTION = "MY_ACTION"

    }

}