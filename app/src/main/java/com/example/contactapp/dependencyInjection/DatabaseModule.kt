package com.example.contactapp.dependencyInjection

import android.content.Context
import androidx.room.Room
import com.example.contactapp.data.local.ContactDao
import com.example.contactapp.data.local.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ContactDatabase {
        return Room.databaseBuilder(
            context,
            ContactDatabase::class.java,
            "contact_database"
        ).build()
    }

    @Provides
    fun provideContactDao(database: ContactDatabase): ContactDao {
        return database.contactDao()
    }
}