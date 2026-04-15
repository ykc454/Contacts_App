package com.example.contactapp.domain.model

data class Contact(
    val id: Int,
    val image: String,
    val name: String,
    val phoneNumber: String,
    val email: String
)