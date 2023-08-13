package com.example.RoomRepositoryVModelSetup.viewmodel

import android.util.Log
import androidx.lifecycle.*
import com.example.RoomRepositoryVModelSetup.model.Datum
import com.example.RoomRepositoryVModelSetup.model.User
import com.example.RoomRepositoryVModelSetup.model.UserResponse
import com.example.RoomRepositoryVModelSetup.repository.UserRepository
import kotlinx.coroutines.*
import java.util.*


class UserViewModel(private val userRepo: UserRepository?) : ViewModel() {
    private var userLiveData : LiveData<UserResponse>
    private var userDetailsLiveData : LiveData<User>

    init {
        userLiveData = userRepo!!.getUserResponseData()
        userDetailsLiveData = userRepo.getUserDetails()
    }

    fun insertUserData(userData:Datum?) {
            viewModelScope.launch {
               val result =  userRepo?.insertUserDataToLocalDb(userData!!)
                Log.d("UserViewModel", "${result}")
            }

    }

    fun getUserResponseData () : LiveData<UserResponse>{
        return userLiveData
    }

    fun getuserDetailsData():LiveData<User>{
        return userDetailsLiveData
    }

    fun getUserData(){
        userRepo?.callUsersDataApi(20)
    }

    fun getUserDetailsDataApi(id: Int){
        userRepo?.getUserDetailsApi(id)

    }

    fun getUserDatafromLocalDb(): List<Datum> {
         var userDataList: List<Datum> = ArrayList<Datum>()
       viewModelScope.launch {
            userDataList = userRepo?.getSavedUserData()!!
        }

        return userDataList
    }

    fun clearTable(){
        viewModelScope.launch {
            userRepo?.clearAllData()
        }
    }

}