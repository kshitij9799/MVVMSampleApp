package com.example.mvvmsampleapp.ui.auth

import com.example.mvvmsampleapp.data.db.entities.User
import retrofit2.Response

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message:String)
}