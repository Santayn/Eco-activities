package com.example.eco.api.dto.filter

data class EventParticipantFilterDTO(
    val userId: Int? = null,
    val eventId: Int? = null,
    val status: String? = null,
    val membershipStatus: String? = null,
    val createdAtFrom: String? = null,
    val createdAtTo: String? = null,
    val page: Int? = null,
    val size: Int? = null
)