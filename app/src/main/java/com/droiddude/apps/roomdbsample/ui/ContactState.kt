package com.droiddude.apps.roomdbsample.ui

import com.droiddude.apps.roomdbsample.data.Contact
import com.droiddude.apps.roomdbsample.interfaces.SortType

data class ContactState(
    val contacts : List<Contact> = emptyList(),
    val firstName : String = "",
    val lastName : String = "",
    val phoneNumber : String = "",
    val isAddingContact : Boolean = false,
    val sortType : SortType = SortType.FIRST_NAME
)
