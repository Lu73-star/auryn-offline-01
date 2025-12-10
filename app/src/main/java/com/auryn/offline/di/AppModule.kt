package com.auryn.offline.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.auryn.offline.data.local.AurynDatabase
import com.auryn.offline.data.local.ConversationDao
import com.auryn.offline.data.local.MessageDao
import com.auryn.offline.data.remote.OpenAIApi
import com.auryn.offline.data.repository.ConversationRepositoryImpl
import com.auryn.offline.data.repository.MessageRepositoryImpl
import com.auryn.offline.data.repository.PreferencesRepositoryImpl
import com.auryn.offline.domain.repository.ConversationRepository
import com.auryn.offline.domain.repository.MessageRepository
import com.auryn.offline.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

/**
 * Dagger Hilt module for dependency injection
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    
    @Provides
    @Singleton
    fun provideAurynDatabase(@ApplicationContext context: Context): AurynDatabase {
        return Room.databaseBuilder(
            context,
            AurynDatabase::class.java,
            AurynDatabase.DATABASE_NAME
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideMessageDao(database: AurynDatabase): MessageDao {
        return database.messageDao()
    }
    
    @Provides
    @Singleton
    fun provideConversationDao(database: AurynDatabase): ConversationDao {
        return database.conversationDao()
    }
    
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }
    
    @Provides
    @Singleton
    fun provideMessageRepository(messageDao: MessageDao): MessageRepository {
        return MessageRepositoryImpl(messageDao)
    }
    
    @Provides
    @Singleton
    fun provideConversationRepository(conversationDao: ConversationDao): ConversationRepository {
        return ConversationRepositoryImpl(conversationDao)
    }
    
    @Provides
    @Singleton
    fun providePreferencesRepository(dataStore: DataStore<Preferences>): PreferencesRepository {
        return PreferencesRepositoryImpl(dataStore)
    }
    
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                    // API key will be added here in the future
                    // .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(request)
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openai.com/") // OpenAI API base URL
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    fun provideOpenAIApi(retrofit: Retrofit): OpenAIApi {
        return retrofit.create(OpenAIApi::class.java)
    }
}
