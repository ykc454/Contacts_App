package com.example.contactapp

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ContactRepository @Inject constructor(private val contactDao: ContactDao){
    val allContact : Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact){
        contactDao.insert(contact)
    }

    suspend fun update(contact: Contact){
        contactDao.update(contact)
    }

    suspend fun delete(contact: Contact){
        contactDao.delete(contact)
    }

    fun searchContacts(query: String): Flow<List<Contact>>{
        return contactDao.searchContacts("%$query%")
    }


}