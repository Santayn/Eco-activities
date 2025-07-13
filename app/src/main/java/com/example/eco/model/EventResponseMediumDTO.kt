package com.example.eco.model

data class EventResponseMediumDTO(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val conducted: Boolean
)