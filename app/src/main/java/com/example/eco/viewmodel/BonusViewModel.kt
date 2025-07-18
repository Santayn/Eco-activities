package com.example.eco.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eco.api.ApiClient
import com.example.eco.api.dto.bonus.BonusHistoryDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BonusViewModel : ViewModel() {

    private val _bonusHistory = MutableLiveData<List<BonusHistoryDto>>()
    val bonusHistory: LiveData<List<BonusHistoryDto>> = _bonusHistory

    private val _totalBalance = MutableLiveData<Int>(0)
    val totalBalance: LiveData<Int> = _totalBalance

    private val bonusApi = ApiClient.bonusService

    fun fetchBonusHistory(userId: Int) {
        bonusApi.getBonusHistory(userId).enqueue(object : Callback<List<BonusHistoryDto>> {
            override fun onResponse(
                call: Call<List<BonusHistoryDto>>,
                response: Response<List<BonusHistoryDto>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val bonuses = response.body()!!
                    _bonusHistory.postValue(bonuses)
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

    private fun calculateTotalBalance(bonuses: List<BonusHistoryDto>) {
        val total = bonuses.sumOf { it.amount }
        _totalBalance.postValue(total)
    }
}