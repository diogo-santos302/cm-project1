package com.example.mydaylogger.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import androidx.compose.ui.node.CanFocusChecker.start
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.mydaylogger.R
import com.example.mydaylogger.ui.MyDayLoggerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
                 MyDayLoggerTopAppBar(title = "MyDayLogger", canNavigateBack = false)
        },
        bottomBar = {
            BottomAppBar (contentPadding = PaddingValues(horizontal = 30.dp)){
                Row (
                    horizontalArrangement = Arrangement.SpaceBetween, //evenly space the icons
                    modifier = Modifier.fillMaxWidth()
                ){
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.home), contentDescription = null )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.goal), contentDescription = null )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(painter = painterResource(id = R.drawable.profile), contentDescription = null )
                    }
                }
            }
        },
        content =  { innerPadding ->
        Column (modifier= modifier
            .padding(
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr) + 8.dp,
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr) + 8.dp
            )
            .fillMaxWidth()){
        HomeBar(title = "Estatisticas", modifier= modifier.fillMaxWidth())
        HomeBar(title = "Log de Atividade Fisica", modifier= modifier.fillMaxWidth())
        HomeBar(title = "Alimentação", modifier= modifier.fillMaxWidth())
    }

    }
    )
}

//@Composable
