package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoginResponseDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterRequestDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.RegisterResponseDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.ApiClient
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi

class AuthRepository {
    private val api: LendlyApi = ApiClient.api

    suspend fun login(request: LoginRequestDto): LoginResponseDto {
        return api.login(request)
    }

    suspend fun register(request: RegisterRequestDto): RegisterResponseDto {
        return api.register(request)
    }
}