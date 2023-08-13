package com.example.RoomRepositoryVModelSetup.appDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.RoomRepositoryVModelSetup.model.Datum

@Database(entities = [Datum::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun getUserDao() :UserDataDao

    companion object{
        private var instance : AppDataBase ?=null
        fun getDatabaseInstance(ctx: Context) : AppDataBase ?{
            if(instance==null){
                instance =
                    Room.databaseBuilder(ctx,
                            AppDataBase::class.java,
                            "app_database").build()

            }
            return instance
        }
    }
}