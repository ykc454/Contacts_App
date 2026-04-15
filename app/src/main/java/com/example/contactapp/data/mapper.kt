package com.example.contactapp.data

import com.example.contactapp.data.model.ContactEntity
import com.example.contactapp.domain.model.Contact

// Entity → Domain
fun ContactEntity.toDomain(): Contact {
    return Contact(
        id = id,
        image = image,
        name = name,
        phoneNumber = phoneNumber,
        email = email
    )
}

// Domain → Entity
fun Contact.toEntity(): ContactEntity {
    return ContactEntity(
        id = id,
        image = image,
        name = name,
        phoneNumber = phoneNumber,
        email = email
    )
}