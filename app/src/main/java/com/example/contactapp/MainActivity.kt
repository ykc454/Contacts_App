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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.contactapp.ui.theme.ContactAppTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact_database"
        ).build()

        val repository = ContactRepository(database.contactDao())

        val viewModel: ContactViewModel by viewModels { ContactViewModelFactory(repository) }
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "contactList"){
                composable("contactList"){
                    ContactListScreen(viewModel,navController)
                }
                composable("addContact"){
                    AddContactScreen(viewModel,navController)
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