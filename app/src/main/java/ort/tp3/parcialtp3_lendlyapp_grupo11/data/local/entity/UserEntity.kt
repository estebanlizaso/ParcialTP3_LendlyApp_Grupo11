package ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val uid: String,
    val email: String,
    val fullName: String,
    val avatar: String,
    val creditScore: Int,
    val accountBalance: Double,
    val birthDate: String? = null,
    val address: String? = null,
    val phone: String = ""
) {
    val creditLevel: String
        get() = when {
            creditScore >= 800 -> "Excellent"
            creditScore >= 700 -> "Good"
            creditScore >= 600 -> "Fair"
            else -> "Poor"
        }
}
