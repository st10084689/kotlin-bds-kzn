package com.bds.kotlinkzn_bds

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {
    private val api: ApiInterface

    init {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://kznbdsapp.online/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiInterface::class.java)
    }

    fun getEvents(): Call<EventsResponse> {
        return api.getEvents()
    }

    fun getShopping(): Call<ShoppingResponse> {
        return api.getShopping()
    }

    fun getPhrases(): Call<PhrasesResponse> {
        return api.getPhrases()
    }

    fun getSigns(): Call<SignsResponse> {
        return api.getSigns()
    }
}
