package com.example.myapplication

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

data class UserModel(
    val name : String,
    val email : String,
    val address : String
)

class SharedViewModel : ViewModel(){
    private val _user = mutableStateOf<UserModel?>(null)
    val user: State<UserModel?> = _user
    fun setUser(newUser: UserModel){
        _user.value = newUser
    }
}