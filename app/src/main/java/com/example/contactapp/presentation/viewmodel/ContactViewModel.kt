package com.example.contactapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.contactapp.domain.model.Contact
import com.example.contactapp.domain.usecase.AddContact
import com.example.contactapp.domain.usecase.DeleteContact
import com.example.contactapp.domain.usecase.GetContacts
import com.example.contactapp.domain.usecase.SearchContacts
import com.example.contactapp.domain.usecase.UpdateContact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val getContacts: GetContacts,
    private val addContact: AddContact,
    private val updateContact: UpdateContact,
    private val deleteContact: DeleteContact,
    private val searchContacts: SearchContacts
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val contacts: LiveData<List<Contact>> = _searchQuery
        .flatMapLatest { query ->
            if (query.isEmpty()) {
                getContacts()
            } else {
                searchContacts(query)
            }
        }
        .asLiveData()

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun addContact(image: String, name: String, phone: String, email: String) {
        viewModelScope.launch {
            addContact(Contact(0, image, name, phone, email))
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            updateContact.invoke(contact)
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            deleteContact.invoke(contact)
        }
    }
}