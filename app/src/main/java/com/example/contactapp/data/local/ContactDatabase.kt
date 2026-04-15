package com.example.contactapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactapp.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1, exportSchema = false)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}