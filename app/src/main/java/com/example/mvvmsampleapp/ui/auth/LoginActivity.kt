package com.example.mvvmsampleapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mvvmsampleapp.R
import com.example.mvvmsampleapp.data.db.AppDatabase
import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.data.network.MyApi
import com.example.mvvmsampleapp.data.network.NetworkConnectionIntercepter
import com.example.mvvmsampleapp.data.repos.UserRepository
import com.example.mvvmsampleapp.databinding.ActivityLoginBinding
import com.example.mvvmsampleapp.ui.home.HomeActivity
import com.example.mvvmsampleapp.util.toast
import retrofit2.Response

class LoginActivity : AppCompatActivity(),AuthListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkConnectionIntercepter = NetworkConnectionIntercepter(this)
        val api = MyApi(networkConnectionIntercepter)
        val db = AppDatabase(this)

        val repository = UserRepository(api, db)
        val factory =AuthViewModelFactory(repository)

        val bunding:ActivityLoginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login)
        val viewModel = ViewModelProviders.of(this,factory)[AuthViewModel::class.java]
        bunding.viewmodel = viewModel

        viewModel.authListener = this

        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
                Intent(this,HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        })

    }

    override fun onStarted() {
        toast("login started")
    }


    override fun onSuccess(user: User) {
        toast("user ${user.firstName} logged in")
    }

    override fun onFailure(message: String) {
        toast(message)
    }
}