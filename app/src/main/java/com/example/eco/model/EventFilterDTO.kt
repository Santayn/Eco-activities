package com.example.eco.model

data class EventFilterDTO(
    val query: String? = null,
    val location: String? = null,
    val startDate: String? = null,
    val endDate: String? = null
)