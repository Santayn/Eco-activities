package com.example.eco.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eco.api.ApiClient
import com.example.eco.api.dto.bonus.BonusHistoryDto
import com.example.eco.api.dto.bonus.BonusTypeDTO
import com.example.eco.api.dto.bonus.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class BonusViewModel : ViewModel() {

    private val _bonusHistory = MutableLiveData<SearchResult<BonusHistoryDto>>()
    val bonusHistory: LiveData<SearchResult<BonusHistoryDto>> = _bonusHistory

    private val _totalBalance = MutableLiveData<Int>(0)
    val totalBalance: LiveData<Int> = _totalBalance

    // 🔥 Новое поле для хранения результатов поиска бонусов
    private val _bonusTypes = MutableLiveData<List<BonusTypeDTO>>()
    val bonusTypes: LiveData<List<BonusTypeDTO>> = _bonusTypes

    private val bonusApi = ApiClient.bonusService

    fun fetchBonusHistory(userId: Int) {
        bonusApi.getBonusHistory(userId).enqueue(object : Callback<List<BonusHistoryDto>> {
            override fun onResponse(
                call: Call<List<BonusHistoryDto>>,
                response: Response<List<BonusHistoryDto>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val bonuses = response.body()!!
                    val dummyResult = SearchResult(
                        content = bonuses,
                        totalPages = 1,
                        totalElements = bonuses.size.toLong(),
                        size = bonuses.size,
                        number = 0
                    )
                    _bonusHistory.postValue(dummyResult)
                    calculateTotalBalance(bonuses)
                } else {
                    Log.e("BonusViewModel", "Ошибка получения данных: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<BonusHistoryDto>>, t: Throwable) {
                Log.e("BonusViewModel", "Ошибка сети: ${t.message}")
            }
        })
    }

    fun searchBonusTypes(name: String?, description: String?, page: Int, size: Int) {
        val filter = mapOf(
            "sortBy" to "createdAt",
            "sortOrder" to "desc"
        )

        bonusApi.searchBonusTypes(name, description, page, size, filter)
            .enqueue(object : Callback<SearchResult<BonusTypeDTO>> {
                override fun onResponse(
                    call: Call<SearchResult<BonusTypeDTO>>,
                    response: Response<SearchResult<BonusTypeDTO>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val result = response.body()!!
                        _bonusTypes.postValue(result.content)
                    } else {
                        Log.e("BonusViewModel", "Ошибка поиска: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<SearchResult<BonusTypeDTO>>, t: Throwable) {
                    Log.e("BonusViewModel", "Ошибка сети при поиске: ${t.message}")
                }
            })
    }

    private fun calculateTotalBalance(bonuses: List<BonusHistoryDto>) {
        val total = bonuses.sumOf { it.amount }
        _totalBalance.postValue(total)
    }
}