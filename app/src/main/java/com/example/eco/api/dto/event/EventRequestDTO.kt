package com.example.eco.api.dto.event

data class EventRequestDTO(
    val id: Int? = null,
    val title: String,
    val description: String,
    val startTime: String, // ISO 8601 date-time
    val endTime: String,
    val location: String,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val conducted: Boolean,
    val eventTypeId: Int,
    val userId: Int
)