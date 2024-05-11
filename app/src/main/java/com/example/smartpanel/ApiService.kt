package com.example.smartpanel

import com.example.smartpanel.model.LoginModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("auth")
    fun loginUser(
        @Body request: LoginModel
    ): Call<LoginResponseData>
}