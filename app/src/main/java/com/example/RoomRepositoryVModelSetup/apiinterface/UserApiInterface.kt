package com.example.RoomRepositoryVModelSetup.apiinterface

import com.example.RoomRepositoryVModelSetup.model.User
import com.example.RoomRepositoryVModelSetup.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
 public interface UserApiInterface {
    @GET("api/users")
    fun callUsersData(@Query("per_page")per_page: Int):Call<UserResponse>

    @GET("api/users/{id}")
    fun getUserDetails(@Path("id") id: Int): Call<User>
}