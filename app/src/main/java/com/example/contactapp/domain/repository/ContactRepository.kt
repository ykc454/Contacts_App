package com.example.contactapp.domain.repository

import com.example.contactapp.domain.model.Contact
import kotlinx.coroutines.flow.Flow

interface ContactRepository {

    fun getAllContacts(): Flow<List<Contact>>

    suspend fun insert(contact: Contact)

    suspend fun update(contact: Contact)

    suspend fun delete(contact: Contact)

    fun searchContacts(query: String): Flow<List<Contact>>
}