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
            if (token != null) {
                addHeader("Authorization", token)
            }
        }.build()

        return chain.proceed(request)
    }
}