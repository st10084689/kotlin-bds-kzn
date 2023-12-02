package com.bds.kotlinkzn_bds

data class SignsResponse(
    val results: Int,
    val signs: List<Sign>,
    val status: String
)