package com.example.contactapp.domain.usecase

import com.example.contactapp.domain.repository.ContactRepository
import javax.inject.Inject

class SearchContacts @Inject constructor(
    private val repository: ContactRepository
) {
    operator fun invoke(query: String) =
        repository.searchContacts(query)
}