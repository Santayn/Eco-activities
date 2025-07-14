package com.example.eco.api.dto.event

import com.example.eco.api.dto.user.UserMediumDTO

data class EventResponseMediumDTO(
    val id: Int,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val location: String,
    val createdAt: String,
    val updatedAt: String,
    val conducted: Boolean,
    val eventType: EventTypeDTO,
    val owner: UserMediumDTO
)