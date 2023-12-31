package com.example.mydaylogger.app.screens

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.mydaylogger.R
import com.example.mydaylogger.app.data.AppContainer
import com.example.mydaylogger.app.data.DatabaseManager
import com.example.mydaylogger.app.data.StorePhoneNumber
import com.example.mydaylogger.app.firebase.UserGender
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch

private const val TAG = "EditProfile"
private const val TAGDB = "DBLOUCA"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navController: NavController,
    context: Context,
    appContainer: AppContainer
    //viewModel: EditProfileViewModel = viewModel(factory= AppViewModelProvider.Factory)
){

    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var emergencyContact by rememberSaveable { mutableStateOf("") }

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var gender by rememberSaveable { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    val dataStore = StorePhoneNumber(context)

    Column (modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(8.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(
                text= "Cancel",
                modifier = Modifier.clickable { navController.popBackStack()}) //navigation necessary
            Text(
                text = "Save",
                modifier = Modifier.clickable {
                    coroutineScope.launch {
                        //Check if requires fields are empty or null
                        Log.d(
                            TAGE,
                            "$name, $age, $height, $weight, $gender, $phoneNumber, $emergencyContact"
                        )
                        if (name.isEmpty() || age.isEmpty() || height.isEmpty()
                            || weight.isEmpty() || gender.isEmpty() || phoneNumber.isEmpty()) {
                            showEmptyFieldErrorNotification(context)
                            return@launch
                        }
//                        val repository = appContainer.userInfoRepository
//                        val id = repository.getNextUserId()
//                        repository.insertUserInfo(
//                            UserInfo(
//                                id = id, // Replace with the actual user ID
//                                name = name,
//                                age = age.toInt(),
//                                height = height.toInt(),
//                                weight = weight.toDouble(),
//                                gender = gender,
//                                phoneNumber = phoneNumber,
//                                emergencyContact = emergencyContact.toInt()
//                            )
//                        )
                        val database = DatabaseManager()
                        Log.d(TAG, "$name, $age, $height, $weight, $gender, $emergencyContact")
                        database.addNewUser(
                            phoneNumber = phoneNumber,
                            name = name,
                            age = age.toInt(),
                            height = height.toDouble(),
                            weight = weight.toDouble(),
                            gender = UserGender.valueOf(gender.uppercase()),
                            firebaseToken = Firebase.messaging.token.result,
                            caretakerPhoneNumber = emergencyContact
                        )
                        dataStore.savePhoneNumber(phoneNumber)
                        navController.popBackStack()
                    }
/*
                    coroutineScope.launch {
                        userInfoRepository.insertUserInfo(UserInfo(
                            id = 0, // Replace with the actual user ID
                            name = name,
                            age = age.toInt(),
                            height = height.toInt(),
                            weight = weight.toDouble(),
                            gender = gender,
                            emergencyContact = emergencyContact.toInt()
                        ))
                    }
*/
                   // navController.popBackStack()
                }
            )
        }

        ProfileImage()

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name:", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange = { name = it },
                singleLine = true,
                keyboardActions = KeyboardActions( //isnt working, tentar por uma next arrow
                    //onNext = {focusRequester.requestFocus()}
                ),
                //modifier = Modifier.focusRequester(focusRequester)
            )
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Age:", modifier = Modifier.width(100.dp))
            TextField(
                value = age,
                onValueChange = { age = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true)

        }

        Spacer(modifier = Modifier.size(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Height (cm):", modifier = Modifier.width(100.dp))
            TextField(
                value = height,
                onValueChange = { height = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true)

        }

        Spacer(modifier = Modifier.size(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Weight (kg):", modifier = Modifier.width(100.dp))
            TextField(
                value = weight,
                onValueChange = { weight = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true)
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Gender:", modifier = Modifier.width(100.dp))
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = {isExpanded = it}
            ) {
                TextField(
                    value = gender,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    modifier = Modifier.menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = "Male") },
                        onClick = {
                            gender = "Male"
                            isExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text(text = "Female") },
                        onClick = {
                            gender = "Female"
                            isExpanded = false
                        }
                    )

                    DropdownMenuItem(
                        text = { Text(text = "Other") },
                        onClick = {
                            gender = "Other"
                            isExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Phone number:", modifier = Modifier.width(100.dp))
            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Gray)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Emergency contact:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.size(10.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Phone number:", modifier = Modifier.width(100.dp))
            TextField(
                value = emergencyContact,
                onValueChange = { emergencyContact = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }
    }
}

    fun showEmptyFieldErrorNotification(context: Context) {
        val dialog = AlertDialog.Builder(context)
            .setTitle("Empty Fields")
            .setMessage("All fields must be filled in.")
            .setPositiveButton("OK", null)
            .create()

        dialog.show()
    }

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ProfileImage() {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberImagePainter(
        if (imageUri.value.isEmpty())
            R.drawable.ic_user
        else
            imageUri.value
    )

    // launch activity to select an image
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri?->
        uri?.let {imageUri.value = it.toString()}
    }

    Column (
        modifier= Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape= CircleShape,
            modifier = Modifier
                .padding(8.dp)
                .size(100.dp)
        ){
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { launcher.launch("image/*") },
                contentScale = ContentScale.Crop
            )
        }
        Text(text ="Change Profile Picture" )
    }
}

