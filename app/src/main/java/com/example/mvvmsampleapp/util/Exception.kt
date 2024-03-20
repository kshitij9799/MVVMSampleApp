package com.example.mvvmsampleapp.util

import android.os.Message
import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)