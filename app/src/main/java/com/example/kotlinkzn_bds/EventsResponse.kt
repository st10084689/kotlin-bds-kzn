package com.example.kotlinkzn_bds

data class EventsResponse(
    var status: String? = null,
    var results: Int?= 0,
    var events: List<Event>? = null
)
