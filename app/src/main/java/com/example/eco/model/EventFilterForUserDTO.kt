package com.example.eco.model

data class EventFilterForUserDTO(
    val userId: Int,
    val status: String? = null
)