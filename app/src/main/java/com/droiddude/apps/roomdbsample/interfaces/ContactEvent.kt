package com.droiddude.apps.roomdbsample.interfaces

import com.droiddude.apps.roomdbsample.data.Contact

sealed interface ContactEvent {

    data class SetFirstName(val firstName : String) : ContactEvent
    data class SetLastName(val lastName : String) : ContactEvent
    data class SetPhoneNumber(val phoneNumber : String) : ContactEvent
    object ShowDialog : ContactEvent
    object HideDialog : ContactEvent
    object SaveContact : ContactEvent
    data class SortContact(val sortType : SortType) : ContactEvent
    data class DeleteContact(val contact : Contact) : ContactEvent
}