// File: AuthInterceptor.kt

package com.example.eco

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val context: Context?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val sharedPreferences = context?.getSharedPreferences("auth", Context.MODE_PRIVATE)
        val token = sharedPreferences?.getString("token", null)

        val request = chain.request().newBuilder().apply {
            if (!token.isNullOrBlank()) {
                val finalToken = if (token.startsWith("Bearer ")) token else "Bearer $token"
                addHeader("Authorization", finalToken)
            }
        }.build()

        return chain.proceed(request)
    }
}