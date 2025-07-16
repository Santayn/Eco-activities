package com.example.eco.api.dto.event

data class Event(
    val id: Int,
    val title: String,
    val description: String,
    val startTime: String, // или LocalDateTime, если используешь парсер
    val endTime: String,
    val location: String,
    val conducted: Boolean,
    val eventType: EventTypeDTO,
    val owner: OwnerDTO
)