package com.example.contactapp.domain.usecase

import com.example.contactapp.domain.model.Contact
import com.example.contactapp.domain.repository.ContactRepository
import javax.inject.Inject

class UpdateContact @Inject constructor(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(contact: Contact) {
        repository.update(contact)
    }
}