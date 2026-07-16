package ort.tp3.parcialtp3_lendlyapp_grupo11.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.AppDatabase
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.LendlyDatabase
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.ProductDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.dao.UserDao
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entity.UserEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.BrandEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.CategoryEntity
import ort.tp3.parcialtp3_lendlyapp_grupo11.data.local.entities.ProductEntity
import java.io.InputStreamReader
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideLendlyDatabase(
        @ApplicationContext context: Context,
        userDaoProvider: Provider<UserDao>
    ): LendlyDatabase {
        return Room.databaseBuilder(
            context,
            LendlyDatabase::class.java,
            "lendly_user_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = userDaoProvider.get()
                    prepopulateUsersFromJson(context, dao)
                }
            }
        })
            .fallbackToDestructiveMigration()
            .build()
    }

    private suspend fun prepopulateUsersFromJson(context: Context, dao: UserDao) {
        try {
            val inputStream = context.assets.open("initial_users.json")
            val reader = InputStreamReader(inputStream)
            val users: List<UserEntity> = Gson().fromJson(reader, object : TypeToken<List<UserEntity>>() {}.type)
            dao.insertUsers(users)
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Provides
    fun provideUserDao(database: LendlyDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        productDaoProvider: Provider<ProductDao>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "lendly_database"
        ).addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    val dao = productDaoProvider.get()
                    prepopulateDatabaseFromJson(context, dao)
                }
            }
        })
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    private suspend fun prepopulateDatabaseFromJson(context: Context, dao: ProductDao) {
        try {
            val inputStream = context.assets.open("products.json")
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val data: Map<String, Any> = Gson().fromJson(reader, type)

            val packageName = context.packageName

            val brandsJson = Gson().toJson(data["brands"])
            val brands: List<BrandEntity> = Gson().fromJson(brandsJson, object : TypeToken<List<BrandEntity>>() {}.type)
            dao.insertBrands(brands.map { it.copy(logo = "android.resource://$packageName/drawable/${it.logo}") })

            val categoriesJson = Gson().toJson(data["categories"])
            val categories: List<CategoryEntity> = Gson().fromJson(categoriesJson, object : TypeToken<List<CategoryEntity>>() {}.type)
            dao.insertCategories(categories)

            val productsJson = Gson().toJson(data["products"])
            val products: List<ProductEntity> = Gson().fromJson(productsJson, object : TypeToken<List<ProductEntity>>() {}.type)
            dao.insertProducts(products.map { it.copy(image = "android.resource://$packageName/drawable/${it.image}") })

            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}
