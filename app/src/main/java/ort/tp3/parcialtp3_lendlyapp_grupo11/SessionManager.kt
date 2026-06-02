package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    // crear archivo de preferencias privado para la app
    private val prefs: SharedPreferences = context.getSharedPreferences("lendly_session", Context.MODE_PRIVATE)

    // guardar token cuando el login es exitoso
    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
    }

    // obtener token (null si no hay sesion iniciada)
    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    // borrar datos (para el log out)
    fun clearSession() {
        prefs.edit().clear().apply()
    }

    // saber si esta loggeado
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}