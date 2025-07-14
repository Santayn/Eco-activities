package com.example.eco.api.dto.event

data class EventTypeDTO(
    val id: Int,
    val name: String,
    val description: String,
    val eventsCount: Int
)