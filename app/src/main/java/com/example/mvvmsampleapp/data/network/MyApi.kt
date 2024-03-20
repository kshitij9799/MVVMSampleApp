package com.example.mvvmsampleapp.data.network

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.mvvmsampleapp.data.db.entities.User
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username")username: String,
        @Field("password")password: String
    ) : Response<User>

    companion object {
        operator fun invoke(
            networkConnectionIntercepter: NetworkConnectionIntercepter
        ):MyApi{
            val okkHttpClient = OkHttpClient.Builder()
                                .addInterceptor(networkConnectionIntercepter)
                                .build()


            return Retrofit.Builder()
                .client(okkHttpClient)
                .baseUrl("https://dummyjson.com/auth/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}