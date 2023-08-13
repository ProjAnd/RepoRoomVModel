package com.example.RoomRepositoryVModelSetup.appDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.RoomRepositoryVModelSetup.model.Datum

@Dao
interface UserDataDao {
    @Insert
    suspend fun insertUserData(obj: Datum)

    @Query("Select * from user_data_table")
    suspend fun getUserDataList(): List<Datum>

    @Query("DELETE from user_data_table")
    suspend fun nukeTable()
}