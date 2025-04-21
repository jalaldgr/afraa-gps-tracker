package com.bornahoosh.afraagpstracker

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

private val Context.dataStore by preferencesDataStore(name = "data_store")

class LoginRepository(private val context: Context) {

    private val tokenKey = stringPreferencesKey("auth_token")

    // بارگذاری توکن از DataStore به صورت suspend
    suspend fun loadToken(): String? {
        return withContext(Dispatchers.IO) {
            context.dataStore.data
                .catch { emit(emptyPreferences()) }
                .map { it[tokenKey] }
                .firstOrNull()
        }
    }

    // لاگین با API و ذخیره توکن در DataStore
    suspend fun login(username: String, password: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val url = URL("https://afragps.com/api/login/")
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "POST"
                conn.setRequestProperty("Content-Type", "application/json")
                conn.doOutput = true
                conn.doInput = true

                // ساخت بدنه‌ی درخواست
                val requestBody = JSONObject().apply {
                    put("username", username)
                    put("password", password)
                }

                // نوشتن داده
                OutputStreamWriter(conn.outputStream).use { writer ->
                    writer.write(requestBody.toString())
                    writer.flush()
                }

                // دریافت پاسخ
                val responseCode = conn.responseCode
                val inputStream = if (responseCode in 200..299) {
                    conn.inputStream
                } else {
                    conn.errorStream
                }

                val responseText = inputStream.bufferedReader().use { it.readText() }
                Log.d("AfraaLoginResponse", responseText)

                if (responseCode == 200) {
                    val json = JSONObject(responseText)
                    json.getString("access")
                } else {
                    null
                }

            } catch (e: Exception) {
                Log.e("AfraaLoginRepository", "Login failed", e)
                null
            }
        }
    }

    // لاگ اوت
    suspend fun logout() {
        withContext(Dispatchers.IO) {
            context.dataStore.edit { preferences ->
                preferences.remove(tokenKey)
            }
        }
    }
}
