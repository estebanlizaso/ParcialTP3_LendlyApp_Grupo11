package ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository

import kotlinx.coroutines.flow.firstOrNull
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.LoansResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.ProductsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.TransactionsResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserNotificationsDto
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.model.UserResponse
import ort.tp3.parcialtp3_lendlyapp_grupo11.network.remote.LendlyApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val api: LendlyApi,
    private val userDao: UserDao,
    private val sessionManager: SessionManager
) {
    suspend fun getUser(id: Int = 1): UserResponse {
        val uid = sessionManager.getToken()
        val localUser = if (uid != null) userDao.getUserById(uid).firstOrNull() else null

        return if (localUser != null) {
            UserResponse(
                success = true,
                user = UserDto(
                    id = id,
                    fullName = localUser.fullName,
                    email = localUser.email,
                    phone = localUser.phone,
                    avatar = localUser.avatar,
                    creditScore = localUser.creditScore,
                    creditLevel = localUser.creditLevel,
                    availableBalance = localUser.accountBalance,
                    address = localUser.address ?: "",
                    birthDate = localUser.birthDate ?: "",
                    totalLoanLimit = 30000.0,
                    memberSince = "2024-01-01",
                    isVerified = true,
                    notifications = UserNotificationsDto(true, true, true)
                )
            )
        } else {
            api.getUser(id)
        }
    }

    suspend fun getLoans(): LoansResponse = api.getLoans()

    suspend fun getProducts(): ProductsResponse = api.getProducts()

    suspend fun getTransactions(): TransactionsResponse = api.getTransactions()
}
