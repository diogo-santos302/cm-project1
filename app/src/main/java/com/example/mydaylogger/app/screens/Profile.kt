package com.example.mydaylogger.app.screens

//import androidx.compose.ui.tooling.data.EmptyGroup.name
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mydaylogger.app.BottomBarScreen
import com.example.mydaylogger.app.data.DatabaseManager
import com.example.mydaylogger.app.data.StorePhoneNumber
import com.example.mydaylogger.app.firebase.MyRealtimeDatabase

const val TAGE = "EDIT"

@Composable
fun ProfileScreen(
    navController: NavHostController
) {

    val context = LocalContext.current
    val dataStore = StorePhoneNumber(context)
    val savedPhoneNumber = dataStore.getPhoneNumber.collectAsState(initial = "edit")
    Log.d(TAGE, "${savedPhoneNumber.value!!}")

    var name: String = ""
    var age: String = ""
    var height: String = ""
    var weight: String = ""
    var gender: String = ""

    val database = DatabaseManager(MyRealtimeDatabase)
    database.getUser(savedPhoneNumber.value!!) {user ->
        if (user != null) {
            var name = user.name
            Log.d(TAGE, "$name")
            var age = user.age.toString()
            val height = user.height.toString()
            val weight = user.weight.toString()
            val gender = user.gender.toString()
            //val caretakerPhoneNumber = user.caretakerPhoneNumber
        } else{
            val name = ""
            val age = ""
            val height = ""
            val weight = ""
            val gender = ""
        }
    }

    Log.d(TAGE, "$name")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Profile Page",
                modifier = Modifier
                    .padding(start = 8.dp),
                fontSize = 24.sp
            )

            Button(
                onClick = {
                    Log.d(TAGE,"lol")
                    navController.navigate(BottomBarScreen.EditProfile.route)
                }
            ) {
                Text(text = "Edit")
            }
        }

        ProfileImage()

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Name:", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "$name", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Age:", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "age", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Height(cm):", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "height", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Weight(kg):", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "weight", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Gender:", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "gender", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(text = "Phone number:", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "${savedPhoneNumber.value!!}", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.size(12.dp))

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
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.size(12.dp))

        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(start = 4.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Phone number:", modifier = Modifier.width(120.dp), fontSize = 20.sp)
            Text(text = "TODO()", fontSize = 20.sp)

        }
    }
}

//@Preview
//@Composable
//fun PreviewProfilePage() {
//    MyDayLoggerTheme {
//        ProfileScreen(navController = navController)
//    }
//}
