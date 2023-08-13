package com.example.RoomRepositoryVModelSetup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.RoomRepositoryVModelSetup.repository.UserRepository

class UserViewModelFactory(private val userRepo: UserRepository?)
    : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(userRepo ) as T
        }
        throw IllegalArgumentException("Unknown VieModel Class")
    }

}