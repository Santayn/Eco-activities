package com.example.eco.api.dto.bonus

data class Bonus(
    val id: Int,
    val user: UserShortDTO,
    val bonusType: BonusTypeDTO,
    val amount: Int,
    val reason: String,
    val createdAt: String,
    val active: Boolean
)