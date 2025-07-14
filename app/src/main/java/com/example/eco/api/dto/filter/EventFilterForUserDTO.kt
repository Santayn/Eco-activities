package com.example.eco.api.dto.filter

data class EventFilterForUserDTO(
    val title: String? = null,
    val description: String? = null,
    val dateFrom: String? = null,
    val dateTo: String? = null,
    val userIdForEventFilter: Int? = null,
    val page: Int? = null,
    val size: Int? = null
)