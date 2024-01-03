package com.droiddude.apps.roomdbsample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droiddude.apps.roomdbsample.data.Contact
import com.droiddude.apps.roomdbsample.data.ContactDao
import com.droiddude.apps.roomdbsample.interfaces.ContactEvent
import com.droiddude.apps.roomdbsample.interfaces.SortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val dao : ContactDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.FIRST_NAME)
    private val _state = MutableStateFlow(ContactState())
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortType.FIRST_NAME -> dao.getContactsByFirstName()
                SortType.LAST_NAME -> dao.getContactsByLastName()
                SortType.PHONE_NUMBER -> dao.getContactsByPhoneNumber()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    val state = combine( _state,_contacts, _sortType) { state, contacts, sortType ->
        state.copy(
            contacts = contacts,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ContactState())

    fun onEvent(event : ContactEvent) {
        when(event) {
            is ContactEvent.DeleteContact -> {
                viewModelScope.launch {
                    dao.deleteContact(event.contact)
                }
            }
            ContactEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingContact = false
                ) }
            }
            ContactEvent.SaveContact -> {
                val firstName = state.value.firstName
                val lastName = state.value.lastName
                val phoneNumber = state.value.phoneNumber
                if(firstName.isBlank() || lastName.isBlank() || phoneNumber.isBlank()) return;
                val contact = Contact(firstName = firstName, lastName = lastName, phoneNumber = phoneNumber)
                viewModelScope.launch {
                    dao.upsertContact(contact)
                }
                _state.update {it.copy(
                    isAddingContact = false,
                    firstName = "",
                    lastName = "",
                    phoneNumber = ""
                )}
            }
            is ContactEvent.SetFirstName -> {
                _state.update { it.copy(
                    firstName = event.firstName
                )}
            }
            is ContactEvent.SetLastName -> {
                _state.update { it.copy(
                    lastName = event.lastName
                )}
            }
            is ContactEvent.SetPhoneNumber -> {
                _state.update { it.copy(
                    phoneNumber = event.phoneNumber
                )}
            }
            ContactEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingContact = true
                )}
            }
            is ContactEvent.SortContact -> {
                _sortType.value = event.sortType
            }
        }
    }
}