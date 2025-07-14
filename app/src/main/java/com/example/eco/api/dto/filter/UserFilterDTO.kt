package com.example.eco.api.dto.filter

data class UserFilterDTO(
    val fullName: String? = null,
    val login: String? = null,
    val registeredEventsCount: Int? = null,
    val totalBonusPoints: Int? = null,
    val role: String? = null,
    val page: Int? = null,
    val size: Int? = null
)