package com.example.mydaylogger.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.node.CanFocusChecker.end
//import androidx.compose.ui.node.CanFocusChecker.end
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.mydaylogger.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val focusRequester = FocusRequester()
    val notification = rememberSaveable{mutableStateOf("")}
    if (notification.value.isNotEmpty()) {
        Toast.makeText(LocalContext.current, notification.value, Toast.LENGTH_LONG).show()
        notification.value = ""
    }

    var name by rememberSaveable { mutableStateOf("") }
    var age by rememberSaveable { mutableStateOf("") }
    var height by rememberSaveable { mutableStateOf("") }
    var weight by rememberSaveable { mutableStateOf("") }

    var isExpanded by rememberSaveable {
        mutableStateOf(false)
    }
    var gender by rememberSaveable { mutableStateOf("") }
    
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
            Text(text= "Cancel", modifier = Modifier.clickable { notification.value = "Cancelled"}) //navigation necessary
            Text(text = "Save", modifier = Modifier.clickable { notification.value= "Saved" } )
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
                    onNext = {focusRequester.requestFocus()}
                ),
                modifier = Modifier.focusRequester(focusRequester)
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
            Text(text = "Height(cm):", modifier = Modifier.width(100.dp))
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
            Text(text = "Weight(kg):", modifier = Modifier.width(100.dp))
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

    }
}


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

@Preview (showBackground = true)
@Composable
fun ProfileScreenPreview(){
    ProfileScreen()
}