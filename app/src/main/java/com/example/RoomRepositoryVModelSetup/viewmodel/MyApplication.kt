package com.example.RoomRepositoryVModelSetup.viewmodel

import android.app.Application
import com.example.RoomRepositoryVModelSetup.appDatabase.AppDataBase
import com.example.RoomRepositoryVModelSetup.repository.UserRepository

class MyApplication : Application() {
     val appDb  = AppDataBase.getDatabaseInstance(this)
     val userRepo  = UserRepository(appDb?.getUserDao()!!)
}