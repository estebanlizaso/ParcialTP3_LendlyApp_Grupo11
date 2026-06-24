package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginResponseDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterResponseDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val api: LendlyApi
) {

    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return api.login(request)
    }

    suspend fun register(request: RegisterRequestDto): RegisterResponseDto {
        return api.register(request)
    }
}