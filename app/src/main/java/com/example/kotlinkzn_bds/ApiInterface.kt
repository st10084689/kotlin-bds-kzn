package com.example.kotlinkzn_bds

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("events")
    fun getEvents(): Call<EventsResponse>

    @GET("products")
    fun getShopping(): Call<ShoppingResponse>
}
