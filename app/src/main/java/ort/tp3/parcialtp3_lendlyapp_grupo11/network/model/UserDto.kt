package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

data class UserResponse(
    val success: Boolean,
    val user: UserDto
)

data class UserDto(
    val id: Int,
    val fullName: String,
    val phone: String,
    val email: String,
    val avatar: String,
    val birthDate: String,
    val address: String,
    val creditScore: Int,
    val creditLevel: String,
    val availableBalance: Double,
    val totalLoanLimit: Double,
    val memberSince: String,
    val isVerified: Boolean,
    val notifications: UserNotificationsDto
)

data class UserNotificationsDto(
    val push: Boolean,
    val email: Boolean,
    val sms: Boolean
)
