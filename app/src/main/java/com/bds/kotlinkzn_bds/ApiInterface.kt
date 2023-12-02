package com.bds.kotlinkzn_bds

import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("events")
    fun getEvents(): Call<EventsResponse>

    @GET("products")
    fun getShopping(): Call<ShoppingResponse>

    @GET("phrases")
    fun getPhrases(): Call<PhrasesResponse>

    @GET("signs")
    fun getSigns(): Call<SignsResponse>
}
