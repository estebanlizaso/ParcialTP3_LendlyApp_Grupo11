package ort.tp3.parcialtp3_lendlyapp_grupo11.network.model

// --- LOGIN ---
data class LoginRequestDto(
    val phone: String,
    val password: String
)

data class LoginResponseDto(
    val success: Boolean,
    val token: String,
    val user: UserDto
)

// --- REGISTER ---
data class RegisterRequestDto(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val phone: String,
    val password: String
)

data class RegisterResponseDto(
    val success: Boolean,
    val token: String
)