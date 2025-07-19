package com.example.eco.api.dto.bonus

data class SearchResult<T>(
    val content: List<T>,
    val totalPages: Int,
    val totalElements: Long,
    val size: Int,
    val number: Int
)