package com.example.jokehub.data.dto

data class JokeListData(
    val current_page: Int,
    val limit: Int,
    val next_page: Int,
    val previous_page: Int,
    val results: List<Result>,
    val search_term: String,
    val status: Int,
    val total_jokes: Int,
    val total_pages: Int
)