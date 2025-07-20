package com.example.eco

import com.example.eco.api.ApiClient
import com.example.eco.api.dto.bonus.UserBonusHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.Result

class BonusRepository {

    fun searchUserBonuses(
        userId: Int? = null,
        bonusTypeId: Int? = null,
        isActive: Boolean? = null,
        createdAtFrom: String? = null,
        createdAtTo: String? = null,
        page: Int = 0,
        size: Int = 10,
        sortBy: String? = null,
        sortOrder: String? = null,
        callback: (Result<UserBonusHistoryResponse>) -> Unit
    ) {
        val filters = mutableMapOf<String, String>()

        userId?.let { filters["userId"] = it.toString() }
        bonusTypeId?.let { filters["bonusTypeId"] = it.toString() }
        isActive?.let { filters["isActive"] = it.toString() }
        createdAtFrom?.let { filters["createdAtFrom"] = it }
        createdAtTo?.let { filters["createdAtTo"] = it }
        page.let { filters["page"] = it.toString() }
        size.let { filters["size"] = it.toString() }
        sortBy?.let { filters["sortBy"] = it }
        sortOrder?.let { filters["sortOrder"] = it }

        val call = ApiClient.bonusApi.searchUserBonusHistory(filters)
        call.enqueue(object : Callback<UserBonusHistoryResponse?> {
            override fun onResponse(
                call: Call<UserBonusHistoryResponse?>,
                response: Response<UserBonusHistoryResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    callback(Result.success(response.body()!!))
                } else {
                    callback(Result.failure(Exception("Ошибка сервера: ${response.code()}")))
                }
            }

            override fun onFailure(call: Call<UserBonusHistoryResponse?>, t: Throwable) {
                callback(Result.failure(t))
            }
        })
    }
}