package com.example.eco.api.dto.filter

data class UserBonusHistoryFilter(
    val userId: Int? = null,
    val bonusTypeId: Int? = null,
    val isActive: Boolean? = null,
    val createdAtFrom: String? = null,
    val createdAtTo: String? = null,
    val sortBy: String? = null,
    val sortOrder: String? = null,
    val page: Int? = null,
    val size: Int? = null
)