package com.example.mvvmsampleapp.data.repos

import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.SafeApiRequest
import retrofit2.Response

class UserRepository(
    private val api: MyApi,
    private val db:AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(username: String,password : String):Response<User> {

        return apiRequest { api.userLogin(username,password) }
    }

    suspend fun saveUser(user: User) = db.getUserDao().insert(user)

    fun getUser() = db.getUserDao().getUser()

}