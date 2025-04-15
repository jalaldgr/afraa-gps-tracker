package com.bornahoosh.afraagpstracker.presentation.login

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.bornahoosh.afraagpstracker.LoginRepository
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = LoginRepository(application.applicationContext)

    // متغیر برای ذخیره توکن
    var token: String? = null
        private set

    // متغیر برای ذخیره پیغام خطا
    var errorMessage: String? = null
        private set

    init {
        // بارگذاری توکن درون coroutine
        viewModelScope.launch {
            token = repository.loadToken()
        }
    }

    fun login(username: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val responseToken = repository.login(username, password)
            if (responseToken != null) {
                token = responseToken
                onSuccess() // وقتی لاگین موفق شد، این تابع اجرا می‌شود
            } else {
                errorMessage = "خطا در ورود: نام کاربری یا رمز عبور اشتباه است"
                Log.e("AfraaLoginViewModel", "Login failed: Invalid credentials, Response: $responseToken")            }
        }
    }

    // متد برای لاگ اوت
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}
