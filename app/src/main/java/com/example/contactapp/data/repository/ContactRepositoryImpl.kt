package com.example.contactapp.data.repository


import com.example.contactapp.data.local.ContactDao
import com.example.contactapp.data.model.ContactEntity
import com.example.contactapp.data.toDomain
import com.example.contactapp.data.toEntity
import com.example.contactapp.domain.model.Contact
import com.example.contactapp.domain.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val contactDao: ContactDao
) : ContactRepository {

    override fun getAllContacts(): Flow<List<Contact>> {
        return contactDao.getAllContacts().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun insert(contact: Contact) {
        contactDao.insert(contact.toEntity())
    }

    override suspend fun update(contact: Contact) {
        contactDao.update(contact.toEntity())
    }

    override suspend fun delete(contact: Contact) {
        contactDao.delete(contact.toEntity())
    }

    override fun searchContacts(query: String): Flow<List<Contact>> {
        return contactDao.searchContacts("%$query%").map { list ->
            list.map { it.toDomain() }
        }
    }
}