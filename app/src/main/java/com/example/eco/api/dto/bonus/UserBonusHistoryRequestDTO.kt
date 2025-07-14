package com.example.eco.api.dto.bonus

data class UserBonusHistoryRequestDTO(
    val id: Int,
    val userId: Int,
    val bonusTypeId: Int,
    val amount: Int,
    val reason: String,
    val createdAt: String, // ISO 8601 date-time
    val active: Boolean
)