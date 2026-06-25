package ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE uid = :uid")
    fun getUserById(uid: String): Flow<UserEntity?>

    @Query("SELECT creditScore FROM users WHERE uid = :uid")
    fun getCreditScore(uid: String): Flow<Int?>

    @Query("UPDATE users SET creditScore = :score WHERE uid = :uid")
    suspend fun updateCreditScore(uid: String, score: Int)

    @Query("UPDATE users SET avatar = :avatar WHERE uid = :uid")
    suspend fun updateAvatar(uid: String, avatar: String)
}
