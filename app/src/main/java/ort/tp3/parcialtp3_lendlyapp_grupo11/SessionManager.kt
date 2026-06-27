package ort.tp3.parcialtp3_lendlyapp_grupo11

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SessionManager(context: Context) {
    // crear archivo de preferencias privado para la app
    private val prefs: SharedPreferences = context.getSharedPreferences("lendly_session", Context.MODE_PRIVATE)
    private val _unreadNotificationCount = MutableStateFlow(0)
    val unreadNotificationCount: StateFlow<Int> = _unreadNotificationCount.asStateFlow()

    private fun archivedNotificationsKey(uid: String): String {
        return "archived_notifications_$uid"
    }

    private fun unreadNotificationsKey(uid: String): String {
        return "unread_notifications_$uid"
    }

    // guardar token cuando el login es exitoso
    fun saveToken(token: String) {
        prefs.edit().putString("auth_token", token).apply()
        refreshUnreadNotificationCount(token)
    }

    // obtener token (null si no hay sesion iniciada)
    fun getToken(): String? {
        return prefs.getString("auth_token", null)
    }

    // borrar datos (para el log out)
    fun clearSession() {
        prefs.edit().clear().apply()
        _unreadNotificationCount.value = 0
    }

    fun getArchivedNotificationIds(uid: String): Set<String> {
        return prefs.getStringSet(archivedNotificationsKey(uid), emptySet()).orEmpty().toSet()
    }

    fun archiveNotificationId(uid: String, notificationId: String) {
        val updatedIds = getArchivedNotificationIds(uid) + notificationId
        prefs.edit().putStringSet(archivedNotificationsKey(uid), updatedIds).apply()
    }

    fun getUnreadNotificationIds(uid: String): Set<String> {
        return prefs.getStringSet(unreadNotificationsKey(uid), emptySet()).orEmpty().toSet()
    }

    fun addUnreadNotificationId(uid: String, notificationId: String) {
        val updatedIds = getUnreadNotificationIds(uid) + notificationId
        prefs.edit().putStringSet(unreadNotificationsKey(uid), updatedIds).apply()
        _unreadNotificationCount.value = updatedIds.size
    }

    fun clearUnreadNotificationIds(uid: String) {
        prefs.edit().remove(unreadNotificationsKey(uid)).apply()
        _unreadNotificationCount.value = 0
    }

    fun refreshUnreadNotificationCount(uid: String) {
        _unreadNotificationCount.value = getUnreadNotificationIds(uid).size
    }

    // saber si esta loggeado
    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}
