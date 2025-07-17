package com.example.eco.api.dto.event

import com.example.eco.api.dto.user.UserMediumDTO

data class EventResponseMediumDTO(
    val id: Int,
    val title: String,
    val description: String?,
    val startTime: String, // Например: "2025-07-16T14:56:41.392Z"
    val endTime: String,
    val location: String?,
    val eventType: EventTypeDTO,
    val owner: OwnerDTO,
    val conducted: Boolean
)