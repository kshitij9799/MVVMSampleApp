package com.example.mvvmsampleapp.ui.auth

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.mvvmsampleapp.data.repos.UserRepository
import com.example.mvvmsampleapp.util.ApiException
import com.example.mvvmsampleapp.util.Coroutines
import com.example.mvvmsampleapp.util.NoInternetException

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var email: String ? = null
    var password: String ? = null
    var authListener:AuthListener? = null

    fun getLoggedInUser() = userRepository.getUser()

    fun onLoginButtonClick(view:View) {
        authListener?.onStarted()
        if (email.isNullOrEmpty()||password.isNullOrEmpty()){
            authListener?.onFailure("Invalid Email or password")
            return
        }

        Coroutines.main {
            try {
                val response = userRepository.userLogin(email!!,password!!)
                response.let {
                    authListener?.onSuccess(it.body()!!)
                    userRepository.saveUser(it.body()!!)
                    return@main
                }
            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e.message!!)
            }

        }
    }
}