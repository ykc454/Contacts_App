package com.example.contactapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contactapp.presentation.screens.AddContactScreen
import com.example.contactapp.presentation.screens.ContactDetailScreen
import com.example.contactapp.presentation.screens.ContactListScreen
import com.example.contactapp.presentation.screens.EditContactScreen
import com.example.contactapp.presentation.viewmodel.ContactViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: ContactViewModel = hiltViewModel()
            val navController = rememberNavController()
            NavHost(navController, startDestination = "contactList"){
                composable("contactList"){
                    ContactListScreen(viewModel, navController)
                }
                composable("addContact"){
                    AddContactScreen(viewModel, navController)
                }
                composable("contactDetail/{contactId}"){backStackEntry ->
                    val contactId = backStackEntry.arguments?.getString("contactId")?.toInt()
                    val contact = viewModel.contacts.observeAsState(initial = emptyList()).value.find{it.id == contactId}
                    contact?.let{ ContactDetailScreen(it, viewModel, navController) }
                }

                composable("editContact/{contactId}"){backStackEntry ->
                    val contactId = backStackEntry.arguments?.getString("contactId")?.toInt()
                    val contact = viewModel.contacts.observeAsState(initial = emptyList()).value.find{it.id == contactId}
                    contact?.let{ EditContactScreen(it, viewModel, navController) }
                }

            }
        }
    }
}



