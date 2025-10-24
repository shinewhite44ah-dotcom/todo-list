package com.example.taskcloud

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.taskcloud.ui.theme.MyNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ToDoList()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoList() {
    MyNavHost()
}

@Preview
@Composable
fun TaskCloudPreview() {
    ToDoList()
}