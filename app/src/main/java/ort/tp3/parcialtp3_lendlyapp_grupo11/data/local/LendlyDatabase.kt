package ort.tp3.parcialtp3_lendlyapp_grupo11.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class LendlyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
