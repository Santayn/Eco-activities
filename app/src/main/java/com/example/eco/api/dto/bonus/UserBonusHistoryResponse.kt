package com.example.eco.api.dto.bonus

data class UserBonusHistoryResponse(
    val content: List<UserBonusHistoryItem?>,
    val totalElements: Long,
    val totalPages: Int,
    val size: Int,
    val number: Int
) {
    // Переименовали метод, чтобы избежать конфликта
    fun getNonNullContent(): List<UserBonusHistoryItem> {
        return content.filterNotNull()
    }
}