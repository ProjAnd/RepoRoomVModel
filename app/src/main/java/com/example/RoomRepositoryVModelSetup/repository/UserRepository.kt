package com.example.RoomRepositoryVModelSetup.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.RoomRepositoryVModelSetup.apiinterface.UserApiInterface
import com.example.RoomRepositoryVModelSetup.appDatabase.UserDataDao
import com.example.RoomRepositoryVModelSetup.model.Datum
import com.example.RoomRepositoryVModelSetup.model.User
import com.example.RoomRepositoryVModelSetup.model.UserResponse
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class UserRepository {
    private val BASE_URL = "https://reqres.in/"
    private var userApiInterface: UserApiInterface?= null
    private var userDataDao : UserDataDao ? =null

    private var userMutableLiveData : MutableLiveData<UserResponse>
    private var userDetailsMutabledata : MutableLiveData<User>

    constructor(userDao: UserDataDao)  {
        userMutableLiveData = MutableLiveData()
        userDetailsMutabledata = MutableLiveData()
        userDataDao = userDao

        val Httpclient = OkHttpClient().newBuilder().build()

        userApiInterface = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(Httpclient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserApiInterface::class.java)
    }

    fun callUsersDataApi(per_page: Int){
          userApiInterface?.callUsersData(per_page)?.enqueue(object: Callback<UserResponse>{
              override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                  userMutableLiveData.postValue(null)
              }

              override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                 if(response.body() !=null){
                     userMutableLiveData.postValue(response.body())
                 }
              }

          })

    }

    fun getUserDetailsApi(id :Int){
        userApiInterface?.getUserDetails(id)?.enqueue(object: Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                userDetailsMutabledata.postValue(null)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if(response.body() !=null){
                    userDetailsMutabledata.postValue(response.body())
                }
            }

        })
    }


    fun getUserResponseData():  LiveData<UserResponse>{
        return userMutableLiveData
    }

    fun getUserDetails():LiveData<User>{
        return userDetailsMutabledata
    }

    suspend fun insertUserDataToLocalDb(userData: Datum){
         userDataDao?.insertUserData(userData)
    }

    suspend fun getSavedUserData(): List<Datum>? {
        return userDataDao?.getUserDataList()
    }

    suspend fun clearAllData(){
        userDataDao?.nukeTable()
    }


}