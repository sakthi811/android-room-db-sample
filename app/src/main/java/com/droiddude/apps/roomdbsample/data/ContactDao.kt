package com.droiddude.apps.roomdbsample.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    // Function to insert and update the contact if exists already
    @Upsert
    suspend fun upsertContact(contact : Contact)

    @Delete
    suspend fun deleteContact(contact : Contact)

    @Query("SELECT * FROM contact ORDER BY firstName ASC")
    fun getContactsByFirstName() : Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY lastName ASC")
    fun getContactsByLastName() : Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY phoneNumber ASC")
    fun getContactsByPhoneNumber() : Flow<List<Contact>>
}