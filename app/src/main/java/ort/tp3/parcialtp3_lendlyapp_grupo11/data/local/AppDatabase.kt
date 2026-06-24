package ort.tp3.parcialtp3_lendlyapp_grupo11.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.ProductDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.BrandEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.CategoryEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class, BrandEntity::class, CategoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
