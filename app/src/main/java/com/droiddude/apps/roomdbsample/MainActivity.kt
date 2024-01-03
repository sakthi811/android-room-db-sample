package com.droiddude.apps.roomdbsample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.droiddude.apps.roomdbsample.data.ContactsDatabase
import com.droiddude.apps.roomdbsample.ui.theme.RoomDBSampleTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            ContactsDatabase::class.java,
            "contacts.db"
        ).build()
    }

    private val viewModel by viewModels<ContactsViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ContactsViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomDBSampleTheme {
                val state by viewModel.state.collectAsState()
                ContactsScreen(state = state, onEvent = viewModel::onEvent)
            }
        }
    }
}

