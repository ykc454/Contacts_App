package com.example.contactapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.contactapp.data.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.Companion.IGNORE)
    suspend fun insert(contactEntity: ContactEntity)

    @Update
    suspend fun update(contactEntity: ContactEntity)

    @Delete
    suspend fun delete(contactEntity: ContactEntity)

    @Query("SELECT * FROM contacts")
    fun getAllContacts(): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contacts WHERE name LIKE :searchQuery")
    fun searchContacts(searchQuery: String): Flow<List<ContactEntity>>
}