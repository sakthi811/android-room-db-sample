package com.droiddude.apps.roomdbsample.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Contact(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val firstName : String,
    val lastName : String,
    val phoneNumber : String
)
