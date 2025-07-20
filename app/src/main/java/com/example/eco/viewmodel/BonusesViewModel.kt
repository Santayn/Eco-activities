package com.example.eco.viewmodel

import BonusApi
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eco.api.ApiClient
import com.example.eco.api.dto.bonus.UserBonusHistoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BonusesViewModel : ViewModel() {

    val bonusHistory = MutableLiveData<UserBonusHistoryResponse?>()
    val error = MutableLiveData<String?>()

    fun getBonusHistory(): LiveData<UserBonusHistoryResponse?> {
        return bonusHistory
    }

    fun getError(): LiveData<String?> {
        return error
    }

    fun loadBonuses(userId: Int, page: Int, size: Int, startDate: String, endDate: String) {
        // Создаем фильтры
        val filters: MutableMap<String, String> = HashMap()
        filters["userId"] = userId.toString()
        filters["page"] = page.toString()
        filters["size"] = size.toString()
        if (!startDate.isEmpty()) filters["createdAtFrom"] = startDate
        if (!endDate.isEmpty()) filters["createdAtTo"] = endDate

        // Преобразуем MutableMap в Map
        val filterMap: Map<String, String> = filters.toMap()

        // Получаем сервис Retrofit
        val apiService: BonusApi = ApiClient.bonusApi

        // Выполняем запрос
        val call: Call<UserBonusHistoryResponse?> = apiService.searchUserBonusHistory(filterMap)
        call.enqueue(object : Callback<UserBonusHistoryResponse?> {
            override fun onResponse(
                call: Call<UserBonusHistoryResponse?>,
                response: Response<UserBonusHistoryResponse?>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    bonusHistory.postValue(response.body())
                } else {
                    error.postValue("Ошибка сервера: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserBonusHistoryResponse?>, t: Throwable) {
                error.postValue("Ошибка сети: ${t.message}")
                Log.e("BonusesViewModel", "Network error", t)
            }
        })
    }
}