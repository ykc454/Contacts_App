package com.example.contactapp.presentation.screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.contactapp.presentation.viewmodel.ContactViewModel
import com.example.contactapp.R
import com.example.contactapp.ui.theme.GreenYc
import com.example.contactapp.utils.copyUriToInternalStorage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddContactScreen(viewModel: ContactViewModel, navController: NavController) {
    val context = LocalContext.current.applicationContext
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var name by remember { mutableStateOf("") }
    var phonenumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.height(48.dp),
                title = {
                    Box(modifier = Modifier
                        .fillMaxHeight()
                        .wrapContentHeight()) {
                        Text("Add Contact", fontSize = 18.sp)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        Toast.makeText(
                            context,
                            "Add Contact",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Icon(painter = painterResource(R.drawable.add_contact_icon),contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    GreenYc,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { PaddingValues->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(PaddingValues)
                .padding(18.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            imageUri?. let{uri ->
                Image(painter = rememberAsyncImagePainter(uri),
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = { launcher.launch("image/*") },
                colors = ButtonDefaults.buttonColors(GreenYc)
                ) {
                Text(text = "Choose Image")
            }
            Spacer(modifier = Modifier.height(16.dp))
            TextField(value = name, onValueChange = { name = it},
                label = {Text("Name")},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            TextField(value = phonenumber, onValueChange = { phonenumber = it},
                label = {Text("PhoneNumber")},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            TextField(value = email, onValueChange = { email = it},
                label = {Text("Email")},
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {

                if(imageUri == null){
                    viewModel.addContact("whatpfp",name,phonenumber,email)
                    navController.navigate("contactList"){
                        popUpTo(0)
                    }
                }
                imageUri?.let{
                    val internalPath = copyUriToInternalStorage(context, it, "$name.jpg")
                    internalPath?.let{path->
                        viewModel.addContact(path,name,phonenumber,email)
                        navController.navigate("contactList"){
                            popUpTo(0)
                        }
                    }
                }


            },
//               enabled = imageUri != null
                colors = ButtonDefaults.buttonColors(GreenYc)
            ) {
                Text("Add Contact")
            }
            Spacer(modifier = Modifier.height(28.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                Text("Note:-", modifier = Modifier.padding(10.dp), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text("Fill all details and add image to enable button", color = Color.Red, modifier = Modifier.padding(10.dp))
            }
        }
    }
}