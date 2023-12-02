package com.bds.kotlinkzn_bds

data class PhrasesResponse(
    val phrases: List<Phrase>,
    val results: Int,
    val status: String
)