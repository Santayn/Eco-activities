package com.example.eco.api.dto.filter

data class UserBonusHistoryFilterDTO(
    val userId: Int? = null,
    val bonusTypeId: Int? = null,
    val createdAtFrom: String? = null,
    val createdAtTo: String? = null,
    val isActive: Boolean? = null,
    val page: Int? = null,
    val size: Int? = null
)