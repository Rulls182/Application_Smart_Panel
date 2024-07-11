package com.example.smartpanel

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://smartpanelapi-h2y47qfuza-uc.a.run.app"

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
// Fetch API Endpoints
fun <T> fetch(call: Call<T>, success: (T?, Map<String, String>?) -> Unit, error: (Int, String) -> Unit) {
    call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                val responseData: T? = response.body()
                val headers: Map<String, String> = response.headers().toMultimap()
                    .mapValues { it.value.joinToString(separator = ", ") }

                success(responseData, headers)
            } else {
                error(response.code(), response.message())
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            // Handle failure
            error(0, t.message ?: "Network request failed")
        }
    })
}
val apiService: ApiService = RetrofitClient.retrofit.create(ApiService::class.java)