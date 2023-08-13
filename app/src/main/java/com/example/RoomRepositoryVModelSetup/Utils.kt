package com.example.RoomRepositoryVModelSetup

import android.content.Context
import android.net.ConnectivityManager

class Utils{

    companion object{
        fun  isNetworkAvailable(context: Context): Boolean {
            val conMgr = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = conMgr.activeNetworkInfo
            return networkInfo !=null && networkInfo.isConnected
        }
    }

}