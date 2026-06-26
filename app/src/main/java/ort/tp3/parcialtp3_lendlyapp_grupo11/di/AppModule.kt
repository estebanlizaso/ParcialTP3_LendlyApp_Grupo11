package ort.tp3.parcialtp3_lendlyapp_grupo11.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ort.tp3.parcialtp3_lendlyapp_grupo11.SessionManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    @Provides
    @Singleton
    fun provideFirestoreRepository(): ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository {
        return ort.tp3.parcialtp3_lendlyapp_grupo11.network.repository.FirestoreRepository()
    }
}
