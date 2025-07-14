package com.example.eco.api.dto.filter

data class EventFilterDTO(
    val keyword: String? = null,
    val eventTypeId: Int? = null,
    val startDateFrom: String? = null,
    val startDateTo: String? = null,
    val userId: Int? = null,
    val page: Int? = null,
    val size: Int? = null
)