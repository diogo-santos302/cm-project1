package com.example.mydaylogger.ui.home

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeBar(title: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { },
        shape = RoundedCornerShape(12.dp),
        modifier = modifier) {
        Text(title)
    }
}

@Composable
@Preview
fun HomeBarPreview(){
    HomeBar(title = "Ola")
}