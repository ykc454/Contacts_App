package com.example.contactapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(private val repository: ContactRepository): ViewModel() {
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val contactList: LiveData<List<Contact>> = _searchQuery
        .flatMapLatest { query ->
            repository.searchContacts(query)
        }
        .asLiveData(viewModelScope.coroutineContext)

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }
    val allcontacts: LiveData<List<Contact>> = repository.allContact.asLiveData()
     fun addContact(image: String, name: String, phoneNumber: String, email: String){
         viewModelScope.launch{
             val contact = Contact(id = 0,image = image,name = name, phoneNumber = phoneNumber,email = email)
             repository.insert(contact)
         }
    }

    fun updateContact(contact: Contact){
        viewModelScope.launch {
            repository.update(contact)
        }
    }

    fun deleteContact(contact: Contact){
        viewModelScope.launch {
            repository.delete(contact)
        }
    }
}

//class ContactViewModelFactory(private val repository: ContactRepository): ViewModelProvider.Factory{
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//
//        if(modelClass.isAssignableFrom(ContactViewModel::class.java)){
//            @Suppress("UNCHECKED_CAST")
//            return ContactViewModel(repository) as T
//        }else{
//            throw IllegalArgumentException("Unknown ViewModel class")
//        }
//
//    }
//}