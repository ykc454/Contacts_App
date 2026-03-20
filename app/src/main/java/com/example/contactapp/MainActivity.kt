package com.example.contactapp

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.contactapp.ui.theme.ContactAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            val viewModel: ContactViewModel = hiltViewModel()
            val navController = rememberNavController()
            NavHost(navController, startDestination = "contactList"){
                composable("contactList"){
                    ContactListScreen(viewModel,navController)
                }
                composable("addContact"){
                    AddContactScreen(viewModel,navController)
                }
                composable("contactDetail/{contactId}"){backStackEntry ->
                    val contactId = backStackEntry.arguments?.getString("contactId")?.toInt()
                    val contact = viewModel.allcontacts.observeAsState(initial = emptyList()).value.find{it.id == contactId}
                    contact?.let{ContactDetailScreen(it,viewModel,navController)}
                }

                composable("editContact/{contactId}"){backStackEntry ->
                    val contactId = backStackEntry.arguments?.getString("contactId")?.toInt()
                    val contact = viewModel.allcontacts.observeAsState(initial = emptyList()).value.find{it.id == contactId}
                    contact?.let{EditContactScreen(it,viewModel,navController)}
                }

            }
        }
    }
}

fun copyUriToInternalStorage(context : Context, uri: Uri,fileName: String): String?{
    val file = File(context.filesDir,fileName)
    return try {
        context.contentResolver.openInputStream(uri)?.use {inputStream ->
            FileOutputStream(file).use {outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file.absolutePath
    }catch (e: Exception){
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ContactAppTheme {

    }
}