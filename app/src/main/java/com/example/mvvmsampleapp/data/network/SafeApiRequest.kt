package com.example.mvvmsampleapp.data.network

import com.example.mvvmsampleapp.data.db.entities.User
import com.example.mvvmsampleapp.util.ApiException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

abstract class SafeApiRequest {

    suspend fun apiRequest(call: suspend () -> Response<User>) : Response<User> {

        val response = call.invoke()

        if (response.isSuccessful) {
            return response
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try{
                    message.append(JSONObject(it).getString("message"))
                }catch (e:Error){}
                message.append("/n")
            }
            message.append("Error code : ${response.code()}")

            throw ApiException(message.toString())
        }
    }

}